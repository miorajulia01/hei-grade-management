package com.example.demo.service;

import com.example.demo.entity.JCourse;
import com.example.demo.entity.JGrade;
import com.example.demo.repository.GradeRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentProgressionService {

    private final GradeRepository gradeRepository;

    public double calculateAverage(String studentId) {
        List<JGrade> grades = gradeRepository.findByStudentId(studentId);

        double totalWeightedScore = 0.0;
        double totalCoefficient = 0.0;

        for (JGrade grade : grades) {
            if (grade.getExam() == null || grade.getScore() == null) {
                continue;
            }

            double coefficient = grade.getExam().getCoefficient();

            totalWeightedScore += grade.getScore() * coefficient;
            totalCoefficient += coefficient;
        }

        return totalCoefficient == 0
                ? 0.0
                : totalWeightedScore / totalCoefficient;
    }

    public int calculateValidatedCredits(String studentId) {
        List<JGrade> grades = gradeRepository.findByStudentId(studentId);

        Map<String, JCourse> validatedCourses = new HashMap<>();

        for (JGrade grade : grades) {
            if (grade.getExam() == null
                    || grade.getExam().getCourse() == null
                    || grade.getScore() == null) {
                continue;
            }

            if (grade.getScore() >= 10) {
                JCourse course = grade.getExam().getCourse();
                validatedCourses.put(course.getId(), course);
            }
        }

        return validatedCourses.values().stream()
                .mapToInt(JCourse::getCredit)
                .sum();
    }

    public boolean isRepeating(String studentId) {
        return calculateAverage(studentId) < 10.0;
    }

    public boolean isAdmitted(String studentId) {
        return calculateAverage(studentId) >= 10.0;
    }

    public boolean hasGraduated(String studentId) {
        return calculateValidatedCredits(studentId) >= 180;
    }
}