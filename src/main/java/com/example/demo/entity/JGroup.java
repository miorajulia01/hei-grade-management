package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "\"group\"")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JGroup {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false)
  private String name;
}
