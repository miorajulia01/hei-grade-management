package com.example.demo.repository;

import com.example.demo.entity.JTeacher;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<JTeacher, String> {
  Optional<JTeacher> findByUserId(String userId);
  List<JTeacher> findByLastNameContainingIgnoreCase(String lastName);
  List<JTeacher> findByFirstNameContainingIgnoreCase(String firstName);
  List<JTeacher> findBySpecialtyContainingIgnoreCase(String specialty);
}