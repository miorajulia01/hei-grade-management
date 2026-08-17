package com.example.demo.service;

import com.example.demo.entity.JExam;
import com.example.demo.entity.JGrade;
import com.example.demo.entity.JGradeHistory;
import com.example.demo.entity.JStudent;
import com.example.demo.entity.JTeacher;
import com.example.demo.mapper.GradeHistoryMapper;
import com.example.demo.mapper.GradeMapper;
import com.example.demo.model.Grade;
import com.example.demo.model.GradeHistory;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.GradeHistoryRepository;
import com.example.demo.repository.GradeRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final GradeHistoryRepository gradeHistoryRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examenRepository;
    private final TeacherRepository teacherRepository;
    private final GradeMapper gradeMapper;
    private final GradeHistoryMapper gradeHistoryMapper;

    public GradeService(
            GradeRepository gradeRepository,
            GradeHistoryRepository gradeHistoryRepository,
            StudentRepository studentRepository,
            ExamRepository examenRepository,
            TeacherRepository teacherRepository,
            GradeMapper gradeMapper,
            GradeHistoryMapper gradeHistoryMapper) {
        this.gradeRepository = gradeRepository;
        this.gradeHistoryRepository = gradeHistoryRepository;
        this.studentRepository = studentRepository;
        this.examenRepository = examenRepository;
        this.teacherRepository = teacherRepository;
        this.gradeMapper = gradeMapper;
        this.gradeHistoryMapper = gradeHistoryMapper;
    }

    @Transactional
    public Grade createGrade(String studentId, String examId, Double score) {
        validateScore(score);

        JStudent student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        JExam exam = examenRepository.findById(examId)
                .orElseThrow(() -> new IllegalArgumentException("Exam not found with ID: " + examId));

        JGrade grade = JGrade.builder()
                .student(student)
                .exam(exam)
                .score(score)
                .build();

        JGrade savedGrade = gradeRepository.save(grade);
        return gradeMapper.toModel(savedGrade);
    }

    @Transactional
    public Grade updateGrade(String gradeId, Double newScore, String reason, String teacherId) {
        validateScore(newScore);
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("A modification reason must be provided when updating a grade.");
        }

        JGrade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new IllegalArgumentException("Grade not found with ID: " + gradeId));

        JTeacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + teacherId));

        Double oldScore = grade.getScore();

        JGradeHistory history = JGradeHistory.builder()
                .grade(grade)
                .teacher(teacher)
                .oldScore(oldScore)
                .newScore(newScore)
                .reason(reason)
                .updatedAt(Instant.now())
                .build();
        gradeHistoryRepository.save(history);

        grade.setScore(newScore);
        JGrade updatedGrade = gradeRepository.save(grade);

        return gradeMapper.toModel(updatedGrade);
    }

    @Transactional(readOnly = true)
    public List<Grade> getGradesByStudent(String studentId) {
        return gradeRepository.findByStudentId(studentId).stream()
                .map(gradeMapper::toModel)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GradeHistory> getGradeHistory(String gradeId) {
        return gradeHistoryRepository.findByGradeIdOrderByUpdatedAtDesc(gradeId).stream()
                .map(gradeHistoryMapper::toModel)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll().stream()
                .map(gradeMapper::toModel)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Grade getGradeById(String id) {
        return gradeRepository.findById(id)
                .map(gradeMapper::toModel)
                .orElseThrow(() -> new IllegalArgumentException("Grade not found with ID: " + id));
    }

    private void validateScore(Double score) {
        if (score == null || score < 0.0 || score > 20.0) {
            throw new IllegalArgumentException("Grade score must be between 0.00 and 20.00.");
        }
    }
}