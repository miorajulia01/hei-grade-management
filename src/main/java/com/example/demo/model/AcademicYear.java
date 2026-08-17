package com.example.demo.model;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcademicYear {
    private String id;
    private String label;
    private LocalDate startDate;
    private LocalDate endDate;
}