package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "semester")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JSemester {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "academic_year_id")
  private JAcademicYear academicYear;

  @Column(nullable = false)
  private String code;

  @Column(nullable = false)
  private String label;

  @Column(name = "\"order\"", nullable = false)
  private Integer order;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;
}
