package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "semester")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JSemester {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false, unique = true)
  private String name;
}
