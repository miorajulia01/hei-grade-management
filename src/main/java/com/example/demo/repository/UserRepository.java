package com.example.demo.repository;

import com.example.demo.entity.JUser;
import com.example.demo.enums.UserRole;
import java.util.List;
import java.util.Optional;

import com.example.demo.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<JUser, String> {
  Optional<JUser> findByEmailIgnoreCase(String email);
  List<JUser> findByRole(UserRole role);
}