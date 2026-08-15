package com.example.demo.model;

import java.time.Instant;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeHistory {
  private String id;
  private Double oldScore;
  private Double newScore;
  private String reason;
  private Instant updatedAt;
  private String teacherId;
}
