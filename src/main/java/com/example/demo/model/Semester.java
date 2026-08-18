package com.example.demo.model;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Semester {
  private String id;
  private AcademicYear academicYear;
  private String code;
  private String label;
  private Integer order;
  private LocalDate startDate;
  private LocalDate endDate;
}
