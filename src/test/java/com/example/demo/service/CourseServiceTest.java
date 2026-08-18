package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JCourse;
import com.example.demo.entity.JProgram;
import com.example.demo.entity.JSemester;
import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.ProgramRepository;
import com.example.demo.repository.SemesterRepository;
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
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ProgramRepository programRepository;

    @Mock
    private SemesterRepository semesterRepository;

    @InjectMocks
    private CourseService courseService;

    private JCourse courseEntity;
    private Course courseModel;

    @BeforeEach
    void setUp() {
        courseEntity =
                JCourse.builder()
                        .id("course-1")
                        .ref("JAVA")
                        .title("Java Programming")
                        .credit(6)
                        .isActive(true)
                        .build();

        courseModel =
                Course.builder()
                        .id("course-1")
                        .ref("JAVA")
                        .title("Java Programming")
                        .credit(6)
                        .isActive(true)
                        .build();
    }

    @Test
    void shouldGetAllCourses() {
        when(courseRepository.findAll())
                .thenReturn(List.of(courseEntity));

        List<Course> result = courseService.getAllCourses();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("course-1", result.get(0).getId());
        assertEquals("JAVA", result.get(0).getRef());
        assertEquals("Java Programming", result.get(0).getTitle());
        assertEquals(6, result.get(0).getCredit());
        assertTrue(result.get(0).getIsActive());

        verify(courseRepository).findAll();
    }

    @Test
    void shouldGetCourseById() {
        when(courseRepository.findById("course-1"))
                .thenReturn(Optional.of(courseEntity));

        Course result = courseService.getCourseById("course-1");

        assertNotNull(result);
        assertEquals("course-1", result.getId());
        assertEquals("JAVA", result.getRef());
        assertEquals("Java Programming", result.getTitle());

        verify(courseRepository).findById("course-1");
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFound() {
        when(courseRepository.findById("unknown"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> courseService.getCourseById("unknown"));

        assertEquals(
                "Course not found with id: unknown",
                exception.getMessage());

        verify(courseRepository).findById("unknown");
    }

    @Test
    void shouldSaveCourseWithoutRelations() {
        when(courseRepository.save(any(JCourse.class)))
                .thenReturn(courseEntity);

        Course result = courseService.saveCourse(courseModel);

        assertNotNull(result);
        assertEquals("course-1", result.getId());
        assertEquals("JAVA", result.getRef());
        assertEquals("Java Programming", result.getTitle());
        assertEquals(6, result.getCredit());
        assertTrue(result.getIsActive());

        ArgumentCaptor<JCourse> captor =
                ArgumentCaptor.forClass(JCourse.class);

        verify(courseRepository).save(captor.capture());

        JCourse savedEntity = captor.getValue();

        assertEquals("course-1", savedEntity.getId());
        assertEquals("JAVA", savedEntity.getRef());
        assertEquals("Java Programming", savedEntity.getTitle());
        assertEquals(6, savedEntity.getCredit());
        assertTrue(savedEntity.getIsActive());

        assertNull(savedEntity.getProgram());
        assertNull(savedEntity.getSemester());
    }

    @Test
    void shouldSaveCourseWithProgram() {
        JProgram program =
                JProgram.builder()
                        .id("program-1")
                        .build();

        Course model =
                Course.builder()
                        .id("course-1")
                        .ref("JAVA")
                        .title("Java Programming")
                        .credit(6)
                        .isActive(true)
                        .program(
                                com.example.demo.model.Program.builder()
                                        .id("program-1")
                                        .build())
                        .build();

        when(programRepository.findById("program-1"))
                .thenReturn(Optional.of(program));

        when(courseRepository.save(any(JCourse.class)))
                .thenReturn(courseEntity);

        courseService.saveCourse(model);

        ArgumentCaptor<JCourse> captor =
                ArgumentCaptor.forClass(JCourse.class);

        verify(courseRepository).save(captor.capture());

        JCourse savedEntity = captor.getValue();

        assertEquals(program, savedEntity.getProgram());

        verify(programRepository).findById("program-1");
    }

    @Test
    void shouldSaveCourseWithSemester() {
        JSemester semester =
                JSemester.builder()
                        .id("semester-1")
                        .build();

        Course model =
                Course.builder()
                        .id("course-1")
                        .ref("JAVA")
                        .title("Java Programming")
                        .credit(6)
                        .isActive(true)
                        .semester(
                                com.example.demo.model.Semester.builder()
                                        .id("semester-1")
                                        .build())
                        .build();

        when(semesterRepository.findById("semester-1"))
                .thenReturn(Optional.of(semester));

        when(courseRepository.save(any(JCourse.class)))
                .thenReturn(courseEntity);

        courseService.saveCourse(model);

        ArgumentCaptor<JCourse> captor =
                ArgumentCaptor.forClass(JCourse.class);

        verify(courseRepository).save(captor.capture());

        JCourse savedEntity = captor.getValue();

        assertEquals(semester, savedEntity.getSemester());

        verify(semesterRepository).findById("semester-1");
    }

    @Test
    void shouldSaveCourseWithProgramAndSemester() {
        JProgram program =
                JProgram.builder()
                        .id("program-1")
                        .build();

        JSemester semester =
                JSemester.builder()
                        .id("semester-1")
                        .build();

        Course model =
                Course.builder()
                        .id("course-1")
                        .ref("JAVA")
                        .title("Java Programming")
                        .credit(6)
                        .isActive(true)
                        .program(
                                com.example.demo.model.Program.builder()
                                        .id("program-1")
                                        .build())
                        .semester(
                                com.example.demo.model.Semester.builder()
                                        .id("semester-1")
                                        .build())
                        .build();

        when(programRepository.findById("program-1"))
                .thenReturn(Optional.of(program));

        when(semesterRepository.findById("semester-1"))
                .thenReturn(Optional.of(semester));

        when(courseRepository.save(any(JCourse.class)))
                .thenReturn(courseEntity);

        courseService.saveCourse(model);

        ArgumentCaptor<JCourse> captor =
                ArgumentCaptor.forClass(JCourse.class);

        verify(courseRepository).save(captor.capture());

        JCourse savedEntity = captor.getValue();

        assertEquals(program, savedEntity.getProgram());
        assertEquals(semester, savedEntity.getSemester());

        verify(programRepository).findById("program-1");
        verify(semesterRepository).findById("semester-1");
    }

    @Test
    void shouldThrowExceptionWhenProgramNotFound() {
        Course model =
                Course.builder()
                        .id("course-1")
                        .ref("JAVA")
                        .title("Java Programming")
                        .credit(6)
                        .program(
                                com.example.demo.model.Program.builder()
                                        .id("unknown")
                                        .build())
                        .build();

        when(programRepository.findById("unknown"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> courseService.saveCourse(model));

        assertEquals("Program not found", exception.getMessage());

        verify(programRepository).findById("unknown");
        verify(courseRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenSemesterNotFound() {
        Course model =
                Course.builder()
                        .id("course-1")
                        .ref("JAVA")
                        .title("Java Programming")
                        .credit(6)
                        .semester(
                                com.example.demo.model.Semester.builder()
                                        .id("unknown")
                                        .build())
                        .build();

        when(semesterRepository.findById("unknown"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> courseService.saveCourse(model));

        assertEquals("Semester not found", exception.getMessage());

        verify(semesterRepository).findById("unknown");
        verify(courseRepository, never()).save(any());
    }
}