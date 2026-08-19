package com.example.demo.service.event;

import com.example.demo.endpoint.event.model.SendTranscriptRequested;
import com.example.demo.entity.JDocExport;
import com.example.demo.entity.JExam;
import com.example.demo.entity.JGrade;
import com.example.demo.entity.JStudent;
import com.example.demo.mail.Email;
import com.example.demo.mail.Mailer;
import com.example.demo.repository.DocExportRepository;
import com.example.demo.repository.GradeRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.GradeService;
import com.example.demo.service.PdfService;
import jakarta.mail.internet.InternetAddress;
import java.io.File;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendTranscriptRequestedService implements Consumer<SendTranscriptRequested> {

  private final StudentRepository studentRepository;
  private final GradeRepository gradeRepository;
  private final GradeService gradeService;
  private final DocExportRepository docExportRepository;
  private final PdfService pdfService;
  private final Mailer mailer;

  @SneakyThrows
  @Override
  public void accept(SendTranscriptRequested event) {
    JStudent student =
        studentRepository
            .findById(event.getStudentId())
            .orElseThrow(
                () -> new RuntimeException("Student not found with id: " + event.getStudentId()));

    String htmlBody = buildTranscriptHtml(student);
    File pdfFile = pdfService.generatePdfFromHtml(htmlBody, "releve_" + student.getStudentNumber());

    Email email =
        new Email(
            new InternetAddress(student.getEmail()),
            List.of(),
            List.of(),
            "Votre releve de notes",
            "Bonjour "
                + student.getFirstName()
                + ", veuillez trouver votre releve de notes en piece jointe.",
            List.of(pdfFile));
    mailer.accept(email);

    JDocExport export =
        JDocExport.builder()
            .user(student.getUser())
            .exportType("TRANSCRIPT")
            .fileName(pdfFile.getName())
            .format("PDF")
            .createdAt(Instant.now())
            .build();
    docExportRepository.save(export);

    pdfFile.deleteOnExit();
  }

  private String buildTranscriptHtml(JStudent student) {
    List<JGrade> grades = gradeRepository.findByStudentId(student.getId());
    Double average = gradeService.calculateWeightedAverage(student.getId());

    String rows =
        grades.stream()
            .filter(g -> g.getExam() != null && g.getExam().getCourse() != null)
            .sorted(Comparator.comparing(g -> g.getExam().getCourse().getRef()))
            .map(this::toRow)
            .collect(Collectors.joining());

    return "<html><body>"
        + "<h2>Releve de notes - "
        + student.getFirstName()
        + " "
        + student.getLastName()
        + " ("
        + student.getStudentNumber()
        + ")</h2>"
        + "<table border=\"1\" cellpadding=\"6\" cellspacing=\"0\">"
        + "<tr><th>Cours</th><th>Examen</th><th>Note</th><th>Coefficient</th></tr>"
        + rows
        + "</table>"
        + "<p><b>Moyenne generale ponderee : "
        + String.format("%.2f", average)
        + " / 20</b></p>"
        + "</body></html>";
  }

  private String toRow(JGrade grade) {
    JExam exam = grade.getExam();
    return "<tr><td>"
        + exam.getCourse().getRef()
        + "</td><td>"
        + exam.getType()
        + "</td><td>"
        + grade.getScore()
        + "</td><td>"
        + exam.getCoefficient()
        + "</td></tr>";
  }
}
