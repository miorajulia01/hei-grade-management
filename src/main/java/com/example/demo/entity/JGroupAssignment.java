package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "group_assignment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JGroupAssignment {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id")
  private JStudent student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id")
  private JGroup group;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "semester_id")
  private JSemester semester;

  @Column(name = "assigned_at")
  private Instant assignedAt;

  @Column(name = "is_active")
  private Boolean isActive;
}