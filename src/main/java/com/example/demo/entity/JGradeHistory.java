package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "grade_history")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JGradeHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "old_score", nullable = false)
  private Double oldScore;

  @Column(name = "new_score", nullable = false)
  private Double newScore;

  @Column(nullable = false)
  private String reason;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "grade_id", nullable = false)
  private JGrade grade;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teacher_id", nullable = false)
  private JTeacher teacher;
}
