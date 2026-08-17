package com.example.demo.repository;

import com.example.demo.entity.JExamen;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamenRepository extends JpaRepository<JExamen, String> {
  List<JExamen> findByCourseId(String courseId);
}
