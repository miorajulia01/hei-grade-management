package com.example.demo.entity;

import com.example.demo.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JTeacher {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", unique = true)
  private JUser user;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  private String specialty;

  @Enumerated(EnumType.STRING)
  private StatusEnum status;
}
