package com.example.demo.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Program {
  private String id;
  private String code;
  private String label;
  private String description;
}