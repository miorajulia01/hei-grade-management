package com.example.demo.model;

import java.time.Instant;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupAssignment {
  private String id;
  private Student student;
  private Group group;
  private Semester semester;
  private Instant assignedAt;
  private Boolean isActive;
}