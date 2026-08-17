package com.example.demo.repository;

import com.example.demo.entity.JCourseTeacher;
import com.example.demo.model.CourseTeacherId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseTeacherRepository extends JpaRepository<JCourseTeacher, CourseTeacherId> {
  List<JCourseTeacher> findByCourseId(String courseId);

  List<JCourseTeacher> findByTeacherId(String teacherId);

  Optional<JCourseTeacher> findByCourseIdAndIsPrimaryTrue(String courseId);
}
