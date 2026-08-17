package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "exam")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JExam {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id")
  private JCourse course;

  @Column(nullable = false)
  private String type;

  private String title;

  @Column(name = "date_exam")
  private Instant dateExam;

  @Column(nullable = false)
  private Double coefficient;

  @Column(name = "\"order\"", nullable = false)
  private Integer order;

  @Column(name = "is_published")
  private Boolean isPublished;
}