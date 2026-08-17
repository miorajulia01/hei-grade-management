package com.example.demo.repository;

import com.example.demo.entity.JGroupAssignment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupAssignmentRepository extends JpaRepository<JGroupAssignment, String> {
  List<JGroupAssignment> findByStudentId(String studentId);
  List<JGroupAssignment> findByGroupId(String groupId);
  List<JGroupAssignment> findByGroupIdAndSemesterId(String groupId, String semesterId);
  Optional<JGroupAssignment> findByStudentIdAndGroupIdAndSemesterId(String studentId, String groupId, String semesterId);
  List<JGroupAssignment> findByGroupIdAndIsActiveTrue(String groupId);
}