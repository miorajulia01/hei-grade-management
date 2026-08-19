package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  @PostMapping("/login")
  public LoginResponseDto login(@Valid @RequestBody LoginRequestDto dto) {
    var authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    String token = jwtService.generateToken(userDetails);

    return LoginResponseDto.builder()
        .token(token)
        .email(userDetails.getUsername())
        .role(userDetails.getUser().getRole().name())
        .build();
  }
}
