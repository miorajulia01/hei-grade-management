package com.example.demo.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
  private String id;
  private AcademicYear academicYear;
  private String ref;
  private String label;
}
