package com.example.demo.repository;

import com.example.demo.entity.JExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenRepository extends JpaRepository<JExamen, String> {
    List<JExamen> findByCourseId(String courseId);
}