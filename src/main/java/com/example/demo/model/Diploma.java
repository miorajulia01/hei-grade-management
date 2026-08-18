package com.example.demo.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Diploma {

    private int rank;
    private String studentNumber;
    private String firstName;
    private String lastName;
    private double average;
}