package com.example.demo.repository;

import com.example.demo.entity.JSemester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<JSemester, String> {
    Optional<JSemester> findByName(String name);
}
