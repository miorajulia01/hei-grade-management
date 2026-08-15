package com.example.demo.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupAssignment {
  private String id;
  private String studentId;
  private String groupId;
}
