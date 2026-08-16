package com.example.demo.repository;

import com.example.demo.entity.JGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<JGrade, String> {
    List<JGrade> findByStudentId(String studentId);
    List<JGrade> findByExamId(String examId);
}