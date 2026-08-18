package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "promotion")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JPromotion {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "academic_year_id")
  private JAcademicYear academicYear;

  @Column(nullable = false)
  private String ref;

  @Column(nullable = false)
  private String label;
}
