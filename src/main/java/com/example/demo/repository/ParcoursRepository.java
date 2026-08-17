package com.example.demo.repository;

import com.example.demo.entity.JProgram;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcoursRepository extends JpaRepository<JProgram, String> {
  Optional<JProgram> findByName(String name);
}
