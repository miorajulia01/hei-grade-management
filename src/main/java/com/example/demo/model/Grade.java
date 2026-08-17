package com.example.demo.model;

import java.time.Instant;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
  private String id;
  private Student student;
  private Exam exam;
  private Double score;
  private Double weightedScore;
  private Boolean isValidated;
  private Instant validatedAt;
}
