package com.example.demo.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
  private String id;
  private String firstName;
  private String lastName;
  private String email;
}
