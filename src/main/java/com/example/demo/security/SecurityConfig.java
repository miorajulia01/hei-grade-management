package com.example.demo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserDetailsService userDetailsService;
  private final JwtAuthFilter jwtAuthFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/", "/auth/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/users", "/teachers", "/students")
                    .hasRole("ADMIN")
                    .requestMatchers(
                        HttpMethod.POST,
                        "/academic-years",
                        "/promotions",
                        "/programs",
                        "/groups",
                        "/group-assignments",
                        "/semesters",
                        "/courses",
                        "/course-teachers")
                    .hasRole("ADMIN")
                    .requestMatchers(
                        HttpMethod.PUT,
                        "/users/**",
                        "/teachers/**",
                        "/students/**",
                        "/academic-years/**",
                        "/promotions/**",
                        "/programs/**",
                        "/groups/**",
                        "/group-assignments/**",
                        "/semesters/**",
                        "/courses/**",
                        "/course-teachers/**")
                    .hasRole("ADMIN")
                    .requestMatchers(
                        HttpMethod.DELETE,
                        "/users/**",
                        "/teachers/**",
                        "/students/**",
                        "/academic-years/**",
                        "/promotions/**",
                        "/programs/**",
                        "/groups/**",
                        "/group-assignments/**",
                        "/semesters/**",
                        "/courses/**",
                        "/course-teachers/**",
                        "/exams/**",
                        "/grades/**")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/exams", "/grades")
                    .hasAnyRole("ADMIN", "TEACHER")
                    .requestMatchers(HttpMethod.PUT, "/grades/**")
                    .hasAnyRole("ADMIN", "TEACHER")
                    .requestMatchers(HttpMethod.GET, "/students", "/grades", "/grade-histories/**")
                    .hasAnyRole("ADMIN", "TEACHER")
                    .requestMatchers(HttpMethod.GET, "/promotions/*/graduates")
                    .hasAnyRole("ADMIN", "TEACHER")
                    .anyRequest()
                    .authenticated())
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
