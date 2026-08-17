package com.example.demo.model;

import java.time.Instant;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeHistory {
  private String id;
  private Grade grade;
  private Teacher teacher;
  private Double oldScore;
  private Double newScore;
  private String reason;
  private Instant modifiedAt;
}