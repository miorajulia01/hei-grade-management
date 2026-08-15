package com.example.demo.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    private String id;
    private Double score;
    private String studentId;
    private String examId;
}