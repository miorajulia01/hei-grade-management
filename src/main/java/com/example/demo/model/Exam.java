package com.example.demo.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Exam {
    private String id;
    private String title;
    private Double coefficient;
    private String courseId;
}