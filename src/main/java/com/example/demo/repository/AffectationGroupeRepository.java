package com.example.demo.repository;

import com.example.demo.entity.JAffectationGroupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AffectationGroupeRepository extends JpaRepository<JAffectationGroupe, String> {
    List<JAffectationGroupe> findByStudentId(String studentId);
    List<JAffectationGroupe> findByGroupId(String groupId);
}