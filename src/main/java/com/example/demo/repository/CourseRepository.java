package com.example.demo.repository;

import com.example.demo.entity.JCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<JCourse, String> {
    List<JCourse> findBySemesterId(String semesterId);
}