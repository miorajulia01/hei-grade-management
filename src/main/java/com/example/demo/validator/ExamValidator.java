package com.example.demo.validator;

import com.example.demo.model.Exam;

public final class ExamValidator {

    private ExamValidator() {}

    public static void validateCoefficient(Double coefficient) {
        if (coefficient == null) {
            throw new IllegalArgumentException("Coefficient cannot be null");
        }

        if (coefficient <= 0 || coefficient > 1) {
            throw new IllegalArgumentException(
                    "Coefficient must be greater than 0 and less than or equal to 1");
        }
    }

    public static void validate(Exam exam) {
        if (exam == null) {
            throw new IllegalArgumentException("Exam cannot be null");
        }

        if (exam.getCourse() == null) {
            throw new IllegalArgumentException(
                    "Exam must be associated with a course");
        }

        if (exam.getType() == null || exam.getType().isBlank()) {
            throw new IllegalArgumentException("Exam type cannot be empty");
        }

        validateCoefficient(exam.getCoefficient());

        if (exam.getOrder() == null || exam.getOrder() < 1) {
            throw new IllegalArgumentException(
                    "Exam order must be greater than or equal to 1");
        }
    }

    public static void validateCoefficientsSum(double totalCoefficient) {
        if (Math.abs(totalCoefficient - 1.0) > 0.000001) {
            throw new IllegalArgumentException(
                    "The sum of exam coefficients must be equal to 1");
        }
    }
}