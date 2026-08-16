package com.example.demo.repository;

import com.example.demo.entity.JParcours;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcoursRepository extends JpaRepository<JParcours, String> {
  Optional<JParcours> findByName(String name);
}
