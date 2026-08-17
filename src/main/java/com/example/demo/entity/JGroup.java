package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "\"group\"")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JGroup {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "program_id")
  private JProgram program;

  @Column(nullable = false)
  private String ref;

  private Integer capacity;
}
