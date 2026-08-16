package com.example.demo.repository;

import com.example.demo.entity.JStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<JStudent, String> {
    Optional<JStudent> findByStudentNumber(String studentNumber);
    List<JStudent> findByPromotionId(String promotionId);
}