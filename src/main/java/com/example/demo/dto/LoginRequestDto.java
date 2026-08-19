package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

  @NotBlank(message = "L'email est obligatoire")
  private String email;

  @NotBlank(message = "Le mot de passe est obligatoire")
  private String password;
}
