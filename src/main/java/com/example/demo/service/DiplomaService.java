package com.example.demo.service;

import com.example.demo.entity.JStudent;
import com.example.demo.model.Diploma;
import com.example.demo.repository.StudentRepository;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiplomaService {

    private final StudentRepository studentRepository;
    private final StudentProgressionService studentProgressionService;

    public List<Diploma> getGraduatesByPromotion(String promotionId) {
        List<Diploma> graduates =
                studentRepository.findByPromotionId(promotionId).stream()
                        .filter(student -> isGraduate(student.getId()))
                        .sorted(
                                Comparator.comparingDouble(
                                                (JStudent student) ->
                                                        studentProgressionService.calculateAverage(student.getId()))
                                        .reversed())
                        .map(this::toDiploma)
                        .toList();

        for (int i = 0; i < graduates.size(); i++) {
            graduates.get(i).setRank(i + 1);
        }

        return graduates;
    }

    public boolean isGraduate(String studentId) {
        return studentProgressionService.hasGraduated(studentId);
    }

    private Diploma toDiploma(JStudent student) {
        return Diploma.builder()
                .rank(0)
                .studentNumber(student.getStudentNumber())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .average(studentProgressionService.calculateAverage(student.getId()))
                .build();
    }
}