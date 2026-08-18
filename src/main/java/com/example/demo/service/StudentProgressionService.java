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

        Map<String, JGrade> finalGrades = getFinalGrades(grades);

        double totalWeightedScore = 0.0;
        double totalCoefficient = 0.0;

        for (JGrade grade : finalGrades.values()) {
            if (grade.getExam() == null || grade.getScore() == null) {
                continue;
            }

            double coefficient = grade.getExam().getCoefficient();

            totalWeightedScore += getFinalScore(grade) * coefficient;
            totalCoefficient += coefficient;
        }

        return totalCoefficient == 0.0
                ? 0.0
                : totalWeightedScore / totalCoefficient;
    }

    public int calculateValidatedCredits(String studentId) {
        List<JGrade> grades = gradeRepository.findByStudentId(studentId);

        Map<String, JGrade> finalGrades = getFinalGrades(grades);
        Map<String, JCourse> validatedCourses = new HashMap<>();

        for (JGrade grade : finalGrades.values()) {
            if (grade.getExam() == null
                    || grade.getExam().getCourse() == null
                    || grade.getScore() == null) {
                continue;
            }

            if (getFinalScore(grade) >= 10.0) {
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
        return calculateValidatedCredits(studentId) >= 180
                && calculateAverage(studentId) >= 10.0;
    }

    private Map<String, JGrade> getFinalGrades(List<JGrade> grades) {
        Map<String, JGrade> finalGrades = new HashMap<>();

        for (JGrade grade : grades) {
            if (grade.getExam() == null
                    || grade.getExam().getCourse() == null) {
                continue;
            }

            String courseId = grade.getExam().getCourse().getId();

            JGrade current = finalGrades.get(courseId);

            if (current == null) {
                finalGrades.put(courseId, grade);
                continue;
            }

            if (isRetake(grade.getExam().getType())) {
                finalGrades.put(courseId, grade);
            } else if (!isRetake(current.getExam().getType())) {
                finalGrades.put(courseId, grade);
            }
        }

        return finalGrades;
    }

    private double getFinalScore(JGrade grade) {
        if (isRetake(grade.getExam().getType())) {
            return Math.min(grade.getScore(), 10.0);
        }

        return grade.getScore();
    }

    private boolean isRetake(String type) {
        return type != null
                && (type.equalsIgnoreCase("RETAKE")
                || type.equalsIgnoreCase("RATTRAPAGE"));
    }
}