package com.example.demo.validator;

import com.example.demo.model.Grade;

public final class GradeValidator {

    private GradeValidator() {}

    public static void validateScore(Double score) {
        if (score == null) {
            throw new IllegalArgumentException("Score cannot be null");
        }

        if (score < 0 || score > 20) {
            throw new IllegalArgumentException("Score must be between 0 and 20");
        }
    }

    public static void validate(Grade grade) {
        if (grade == null) {
            throw new IllegalArgumentException("Grade cannot be null");
        }

        if (grade.getStudent() == null) {
            throw new IllegalArgumentException("Grade must be associated with a student");
        }

        if (grade.getExam() == null) {
            throw new IllegalArgumentException("Grade must be associated with an exam");
        }

        validateScore(grade.getScore());
    }

    public static void validateUpdate(Double score, String reason) {
        validateScore(score);

        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException(
                    "A reason is required when updating a grade");
        }
    }
}