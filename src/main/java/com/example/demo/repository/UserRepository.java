package com.example.demo.repository;

import com.example.demo.entity.JUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<JUser, String> {
  Optional<JUser> findByEmail(String email);
}
