package com.example.demo.validator;

import java.util.Map;

public final class GraduateValidator {

  private GraduateValidator() {}

  public static boolean isGraduate(Map<String, Double> finalScoresByCourse) {
    if (finalScoresByCourse == null || finalScoresByCourse.isEmpty()) {
      return false;
    }

    return finalScoresByCourse.values().stream().allMatch(score -> score != null && score >= 10.0);
  }

  public static void validateGraduate(Map<String, Double> finalScoresByCourse) {
    if (!isGraduate(finalScoresByCourse)) {
      throw new IllegalArgumentException("The student does not meet the graduation requirements");
    }
  }
}
