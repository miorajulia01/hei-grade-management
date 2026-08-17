package com.example.demo.repository;

import com.example.demo.entity.JSemester;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<JSemester, String> {
  Optional<JSemester> findByCode(String code);
  List<JSemester> findByAcademicYearId(String academicYearId);
}