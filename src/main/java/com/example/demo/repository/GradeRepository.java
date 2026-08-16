package com.example.demo.repository;

import com.example.demo.entity.JGrade;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<JGrade, String> {
  List<JGrade> findByStudentId(String studentId);

  List<JGrade> findByExamId(String examId);
}
