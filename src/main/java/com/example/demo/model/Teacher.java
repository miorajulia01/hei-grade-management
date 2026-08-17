package com.example.demo.model;

import com.example.demo.enums.StatusEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
  private String id;
  private User user;
  private String firstName;
  private String lastName;
  private String specialty;
  private StatusEnum status;
}