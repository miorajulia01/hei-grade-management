package com.example.demo.repository;

import com.example.demo.entity.JGroup;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<JGroup, String> {
  Optional<JGroup> findByRef(String ref);

  List<JGroup> findByProgramId(String programId);
}
