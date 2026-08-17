package com.example.demo.entity;

import com.example.demo.enums.StatusEnum;
import com.example.demo.enums.UserRole;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JUser {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole role;

  @Column(name = "created_at")
  private Instant createdAt;

  @Enumerated(EnumType.STRING)
  private StatusEnum status;
}
