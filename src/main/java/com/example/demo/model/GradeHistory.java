package com.example.demo.model;

import lombok.*;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeHistory {
    private String id;
    private Double oldScore;
    private Double newScore;
    private String reason;
    private Instant updatedAt;
    private String teacherId;
}