package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "program")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JProgram {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false)
  private String code;

  @Column(nullable = false)
  private String label;

  private String description;
}
