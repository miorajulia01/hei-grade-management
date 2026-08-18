package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGradeDto {

    @NotNull(message = "Le score ne peut pas être nul")
    @Min(value = 0, message = "Le score minimal est 0")
    @Max(value = 20, message = "Le score maximal est 20")
    private Double score;

    private String reason;
}