package com.example.demo.repository;

import com.example.demo.entity.JCourse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<JCourse, String> {
  Optional<JCourse> findByRefIgnoreCase(String ref);
  List<JCourse> findBySemesterId(String semesterId);
  List<JCourse> findByProgramId(String programId);
  List<JCourse> findByIsActiveTrue();
  List<JCourse> findByTitleContainingIgnoreCase(String title);
}