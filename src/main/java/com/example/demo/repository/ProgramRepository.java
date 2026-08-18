package com.example.demo.repository;

import com.example.demo.entity.JProgram;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends JpaRepository<JProgram, String> {
  Optional<JProgram> findByCode(String code);

  Optional<JProgram> findByLabelIgnoreCase(String label);
}
