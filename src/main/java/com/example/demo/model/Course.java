package com.example.demo.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {
  private String id;
  private String code;
  private String name;
  private Integer credits;
}
