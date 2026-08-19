package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JCourse;
import com.example.demo.entity.JCourseTeacher;
import com.example.demo.entity.JTeacher;
import com.example.demo.model.CourseTeacher;
import com.example.demo.model.CourseTeacherId;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.CourseTeacherRepository;
import com.example.demo.repository.TeacherRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourseTeacherServiceTest {

  @Mock private CourseTeacherRepository courseTeacherRepository;

  @Mock private CourseRepository courseRepository;

  @Mock private TeacherRepository teacherRepository;

  @InjectMocks private CourseTeacherService courseTeacherService;

  private JCourseTeacher courseTeacherEntity;
  private CourseTeacher courseTeacherModel;

  @BeforeEach
  void setUp() {
    courseTeacherEntity = JCourseTeacher.builder().isPrimary(true).build();

    courseTeacherModel = CourseTeacher.builder().isPrimary(true).build();
  }

  @Test
  void shouldGetAllCourseTeachers() {
    when(courseTeacherRepository.findAll()).thenReturn(List.of(courseTeacherEntity));

    List<CourseTeacher> result = courseTeacherService.getAllCourseTeachers();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertTrue(result.get(0).getIsPrimary());

    verify(courseTeacherRepository).findAll();
  }

  @Test
  void shouldSaveCourseTeacherWithoutRelations() {
    when(courseTeacherRepository.save(any(JCourseTeacher.class))).thenReturn(courseTeacherEntity);

    CourseTeacher result = courseTeacherService.saveCourseTeacher(courseTeacherModel);

    assertNotNull(result);
    assertTrue(result.getIsPrimary());

    ArgumentCaptor<JCourseTeacher> captor = ArgumentCaptor.forClass(JCourseTeacher.class);

    verify(courseTeacherRepository).save(captor.capture());

    JCourseTeacher savedEntity = captor.getValue();

    assertTrue(savedEntity.getIsPrimary());
    assertNull(savedEntity.getCourse());
    assertNull(savedEntity.getTeacher());
  }

  @Test
  void shouldSaveCourseTeacherWithCourse() {
    JCourse course = JCourse.builder().id("course-1").ref("JAVA").title("Java Programming").build();

    CourseTeacher model =
        CourseTeacher.builder()
            .isPrimary(true)
            .course(com.example.demo.model.Course.builder().id("course-1").build())
            .build();

    when(courseRepository.findById("course-1")).thenReturn(Optional.of(course));

    when(courseTeacherRepository.save(any(JCourseTeacher.class))).thenReturn(courseTeacherEntity);

    courseTeacherService.saveCourseTeacher(model);

    ArgumentCaptor<JCourseTeacher> captor = ArgumentCaptor.forClass(JCourseTeacher.class);

    verify(courseTeacherRepository).save(captor.capture());

    JCourseTeacher savedEntity = captor.getValue();

    assertEquals(course, savedEntity.getCourse());

    verify(courseRepository).findById("course-1");
  }

  @Test
  void shouldSaveCourseTeacherWithTeacher() {
    JTeacher teacher = JTeacher.builder().id("teacher-1").build();

    CourseTeacher model =
        CourseTeacher.builder()
            .isPrimary(true)
            .teacher(com.example.demo.model.Teacher.builder().id("teacher-1").build())
            .build();

    when(teacherRepository.findById("teacher-1")).thenReturn(Optional.of(teacher));

    when(courseTeacherRepository.save(any(JCourseTeacher.class))).thenReturn(courseTeacherEntity);

    courseTeacherService.saveCourseTeacher(model);

    ArgumentCaptor<JCourseTeacher> captor = ArgumentCaptor.forClass(JCourseTeacher.class);

    verify(courseTeacherRepository).save(captor.capture());

    JCourseTeacher savedEntity = captor.getValue();

    assertEquals(teacher, savedEntity.getTeacher());

    verify(teacherRepository).findById("teacher-1");
  }

  @Test
  void shouldSaveCourseTeacherWithCourseAndTeacher() {
    JCourse course = JCourse.builder().id("course-1").ref("JAVA").title("Java Programming").build();

    JTeacher teacher = JTeacher.builder().id("teacher-1").build();

    CourseTeacher model =
        CourseTeacher.builder()
            .isPrimary(true)
            .course(com.example.demo.model.Course.builder().id("course-1").build())
            .teacher(com.example.demo.model.Teacher.builder().id("teacher-1").build())
            .build();

    when(courseRepository.findById("course-1")).thenReturn(Optional.of(course));

    when(teacherRepository.findById("teacher-1")).thenReturn(Optional.of(teacher));

    when(courseTeacherRepository.save(any(JCourseTeacher.class))).thenReturn(courseTeacherEntity);

    courseTeacherService.saveCourseTeacher(model);

    ArgumentCaptor<JCourseTeacher> captor = ArgumentCaptor.forClass(JCourseTeacher.class);

    verify(courseTeacherRepository).save(captor.capture());

    JCourseTeacher savedEntity = captor.getValue();

    assertEquals(course, savedEntity.getCourse());
    assertEquals(teacher, savedEntity.getTeacher());
    assertTrue(savedEntity.getIsPrimary());

    verify(courseRepository).findById("course-1");
    verify(teacherRepository).findById("teacher-1");
  }

  @Test
  void shouldThrowExceptionWhenCourseNotFound() {
    CourseTeacher model =
        CourseTeacher.builder()
            .isPrimary(true)
            .course(com.example.demo.model.Course.builder().id("unknown").build())
            .build();

    when(courseRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> courseTeacherService.saveCourseTeacher(model));

    assertEquals("Course not found", exception.getMessage());

    verify(courseRepository).findById("unknown");
    verify(courseTeacherRepository, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenTeacherNotFound() {
    CourseTeacher model =
        CourseTeacher.builder()
            .isPrimary(true)
            .teacher(com.example.demo.model.Teacher.builder().id("unknown").build())
            .build();

    when(teacherRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> courseTeacherService.saveCourseTeacher(model));

    assertEquals("Teacher not found", exception.getMessage());

    verify(teacherRepository).findById("unknown");
    verify(courseTeacherRepository, never()).save(any());
  }

  @Test
  void shouldGetCourseTeacherById() {
    CourseTeacherId id = new CourseTeacherId("course-1", "teacher-1");

    when(courseTeacherRepository.findById(id)).thenReturn(Optional.of(courseTeacherEntity));

    CourseTeacher result = courseTeacherService.getCourseTeacherById("course-1", "teacher-1");

    assertNotNull(result);
    assertTrue(result.getIsPrimary());

    verify(courseTeacherRepository).findById(id);
  }

  @Test
  void shouldThrowExceptionWhenCourseTeacherNotFound() {
    CourseTeacherId id = new CourseTeacherId("unknown-course", "unknown-teacher");

    when(courseTeacherRepository.findById(id)).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> courseTeacherService.getCourseTeacherById("unknown-course", "unknown-teacher"));

    assertEquals(
        "CourseTeacher not found with courseId: unknown-course and teacherId: unknown-teacher",
        exception.getMessage());

    verify(courseTeacherRepository).findById(id);
  }

  @Test
  void shouldUpdateCourseTeacher() {
    CourseTeacherId id = new CourseTeacherId("course-1", "teacher-1");

    when(courseTeacherRepository.findById(id)).thenReturn(Optional.of(courseTeacherEntity));

    CourseTeacher model = CourseTeacher.builder().isPrimary(false).build();

    JCourseTeacher updatedEntity = JCourseTeacher.builder().isPrimary(false).build();

    when(courseTeacherRepository.save(any(JCourseTeacher.class))).thenReturn(updatedEntity);

    CourseTeacher result = courseTeacherService.updateCourseTeacher("course-1", "teacher-1", model);

    assertNotNull(result);
    assertFalse(result.getIsPrimary());

    verify(courseTeacherRepository).findById(id);
    verify(courseTeacherRepository).save(any(JCourseTeacher.class));
  }

  @Test
  void shouldThrowExceptionWhenUpdatingCourseTeacherNotFound() {
    CourseTeacherId id = new CourseTeacherId("unknown-course", "unknown-teacher");

    when(courseTeacherRepository.findById(id)).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () ->
                courseTeacherService.updateCourseTeacher(
                    "unknown-course", "unknown-teacher", CourseTeacher.builder().build()));

    assertEquals(
        "CourseTeacher not found with courseId: unknown-course and teacherId: unknown-teacher",
        exception.getMessage());

    verify(courseTeacherRepository).findById(id);
    verify(courseTeacherRepository, never()).save(any());
  }

  @Test
  void shouldDeleteCourseTeacher() {
    CourseTeacherId id = new CourseTeacherId("course-1", "teacher-1");

    when(courseTeacherRepository.existsById(id)).thenReturn(true);

    courseTeacherService.deleteCourseTeacher("course-1", "teacher-1");

    verify(courseTeacherRepository).deleteById(id);
  }

  @Test
  void shouldThrowExceptionWhenDeletingCourseTeacherNotFound() {
    CourseTeacherId id = new CourseTeacherId("unknown-course", "unknown-teacher");

    when(courseTeacherRepository.existsById(id)).thenReturn(false);

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> courseTeacherService.deleteCourseTeacher("unknown-course", "unknown-teacher"));

    assertEquals(
        "CourseTeacher not found with courseId: unknown-course and teacherId: unknown-teacher",
        exception.getMessage());

    verify(courseTeacherRepository, never()).deleteById(id);
  }
}
