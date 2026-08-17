package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JCourse {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "semester_id")
  private JSemester semester;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "program_id")
  private JProgram program;

  @Column(nullable = false)
  private String ref;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private Integer credit;

  private String type;

  @Column(name = "is_active")
  private Boolean isActive;
}