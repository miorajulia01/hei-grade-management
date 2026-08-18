package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "grade")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JGrade {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id")
  private JStudent student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exam_id")
  private JExam exam;

  private Double score;

  @Column(name = "weighted_score")
  private Double weightedScore;

  @Column(name = "is_validated")
  private Boolean isValidated;

  @Column(name = "validated_at")
  private Instant validatedAt;
}
