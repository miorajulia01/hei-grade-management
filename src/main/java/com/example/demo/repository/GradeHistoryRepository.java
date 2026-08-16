package com.example.demo.repository;

import com.example.demo.entity.JGradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeHistoryRepository extends JpaRepository<JGradeHistory, String> {
    List<JGradeHistory> findByGradeIdOrderByUpdatedAtDesc(String gradeId);
}