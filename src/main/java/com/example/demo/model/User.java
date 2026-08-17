package com.example.demo.model;

import com.example.demo.enums.StatusEnum;
import com.example.demo.enums.UserRole;
import java.time.Instant;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private String id;
  private String email;
  private String password;
  private UserRole role;
  private Instant createdAt;
  private StatusEnum status;
}