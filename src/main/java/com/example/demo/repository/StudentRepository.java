package com.example.demo.repository;

import com.example.demo.entity.JStudent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<JStudent, String> {
  Optional<JStudent> findByStudentNumber(String studentNumber);

  Optional<JStudent> findByEmailIgnoreCase(String email);

  Optional<JStudent> findByUserId(String userId);

  List<JStudent> findByPromotionId(String promotionId);

  List<JStudent> findByLastNameIgnoreCaseContaining(String lastName);
}
