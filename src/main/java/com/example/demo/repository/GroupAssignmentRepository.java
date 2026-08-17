package com.example.demo.repository;

import com.example.demo.entity.JGroupAssignment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffectationGroupeRepository extends JpaRepository<JGroupAssignment, String> {
  List<JGroupAssignment> findByStudentId(String studentId);

  List<JGroupAssignment> findByGroupId(String groupId);
}
