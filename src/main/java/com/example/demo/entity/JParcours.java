package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "parcours")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JParcours {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;
}