package com.example.demo.repository;

import com.example.demo.entity.JTeacher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<JTeacher, String> {
  List<JTeacher> findByLastNameContainingIgnoreCase(String lastName);

  List<JTeacher> findByFirstNameContainingIgnoreCase(String firstName);
}
