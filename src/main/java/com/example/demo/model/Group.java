package com.example.demo.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Group {
  private String id;
  private Program program;
  private String ref;
  private Integer capacity;
}