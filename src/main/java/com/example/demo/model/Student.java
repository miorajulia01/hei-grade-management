package com.example.demo.model;

import com.example.demo.enums.StatusEnum;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
  private String id;
  private Promotion promotion;
  private User user;
  private String studentNumber;
  private String firstName;
  private String lastName;
  private String email;
  private StatusEnum status;
  private LocalDate dateEnroll;
}