package com.example.demo.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {
  private String id;
  private Semester semester;
  private Program program;
  private String ref;
  private String title;
  private Integer credit;
  private String type;
  private Boolean isActive;
}
