package com.example.demo.repository;

import com.example.demo.entity.JAffectationGroupe;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffectationGroupeRepository extends JpaRepository<JAffectationGroupe, String> {
  List<JAffectationGroupe> findByStudentId(String studentId);

  List<JAffectationGroupe> findByGroupId(String groupId);
}
