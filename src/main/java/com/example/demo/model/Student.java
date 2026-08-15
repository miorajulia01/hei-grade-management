package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
  private String id;
  private String studentNumber;
  private String firstName;
  private String lastName;
  private String email;
  private String promotionName;
}
