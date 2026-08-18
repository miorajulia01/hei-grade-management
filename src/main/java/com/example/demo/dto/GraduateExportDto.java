package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraduateExportDto {
    private Integer rank;
    private String studentNumber;
    private String firstName;
    private String lastName;
    private Double generalAverage;
}