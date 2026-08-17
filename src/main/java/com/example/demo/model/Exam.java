package com.example.demo.model;

import java.time.Instant;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exam {
  private String id;
  private Course course;
  private String type;
  private String title;
  private Instant dateExam;
  private Double coefficient;
  private Integer order;
  private Boolean isPublished;
}
