package com.example.demo.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Promotion {
    private String id;
    private String name;
}