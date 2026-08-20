package com.example.demo.config;

import com.example.demo.entity.JUser;
import com.example.demo.enums.StatusEnum;
import com.example.demo.enums.UserRole;
import com.example.demo.repository.UserRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminBootstrapRunner implements CommandLineRunner {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${admin.seed.email:admin@hei.school}")
  private String adminEmail;

  @Value("${admin.seed.password:Admin123!}")
  private String adminPassword;

  @Override
  public void run(String... args) {
    boolean adminAlreadyExists = !userRepository.findByRole(UserRole.ADMIN).isEmpty();

    if (adminAlreadyExists) {
      log.info("Admin bootstrap : un ADMIN existe deja, rien a faire.");
      return;
    }

    JUser admin =
        JUser.builder()
            .email(adminEmail)
            .password(passwordEncoder.encode(adminPassword))
            .role(UserRole.ADMIN)
            .status(StatusEnum.ACTIVE)
            .createdAt(Instant.now())
            .build();

    userRepository.save(admin);
    log.info("Admin bootstrap : compte ADMIN cree avec l'email {}", adminEmail);
  }
}
