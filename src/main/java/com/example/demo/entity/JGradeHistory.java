package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "grade_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JGradeHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "grade_id")
  private JGrade grade;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teacher_id")
  private JTeacher teacher;

  @Column(name = "old_score")
  private Double oldScore;

  @Column(name = "new_score")
  private Double newScore;

  private String reason;

  @Column(name = "modified_at")
  private Instant modifiedAt;
}