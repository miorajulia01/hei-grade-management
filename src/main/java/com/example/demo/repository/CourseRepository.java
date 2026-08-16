package com.example.demo.repository;

import com.example.demo.entity.JCourse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<JCourse, String> {
  List<JCourse> findBySemesterId(String semesterId);
}
