package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JGroup;
import com.example.demo.entity.JGroupAssignment;
import com.example.demo.entity.JSemester;
import com.example.demo.entity.JStudent;
import com.example.demo.model.GroupAssignment;
import com.example.demo.repository.GroupAssignmentRepository;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.SemesterRepository;
import com.example.demo.repository.StudentRepository;
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
class GroupAssignmentServiceTest {

  @Mock private GroupAssignmentRepository groupAssignmentRepository;

  @Mock private StudentRepository studentRepository;

  @Mock private GroupRepository groupRepository;

  @Mock private SemesterRepository semesterRepository;

  @InjectMocks private GroupAssignmentService groupAssignmentService;

  private JGroupAssignment assignmentEntity;
  private GroupAssignment assignmentModel;

  @BeforeEach
  void setUp() {
    assignmentEntity = JGroupAssignment.builder().id("assignment-1").isActive(true).build();

    assignmentModel = GroupAssignment.builder().id("assignment-1").isActive(true).build();
  }

  @Test
  void shouldGetAllGroupAssignments() {
    when(groupAssignmentRepository.findAll()).thenReturn(List.of(assignmentEntity));

    List<GroupAssignment> result = groupAssignmentService.getAllGroupAssignments();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("assignment-1", result.get(0).getId());
    assertTrue(result.get(0).getIsActive());

    verify(groupAssignmentRepository).findAll();
  }

  @Test
  void shouldGetGroupAssignmentById() {
    when(groupAssignmentRepository.findById("assignment-1"))
        .thenReturn(Optional.of(assignmentEntity));

    GroupAssignment result = groupAssignmentService.getGroupAssignmentById("assignment-1");

    assertNotNull(result);
    assertEquals("assignment-1", result.getId());

    verify(groupAssignmentRepository).findById("assignment-1");
  }

  @Test
  void shouldThrowExceptionWhenGroupAssignmentNotFound() {
    when(groupAssignmentRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(
            RuntimeException.class, () -> groupAssignmentService.getGroupAssignmentById("unknown"));

    assertEquals("GroupAssignment not found with id: unknown", exception.getMessage());

    verify(groupAssignmentRepository).findById("unknown");
  }

  @Test
  void shouldSaveGroupAssignmentWithoutRelations() {
    when(groupAssignmentRepository.save(any(JGroupAssignment.class))).thenReturn(assignmentEntity);

    GroupAssignment result = groupAssignmentService.saveGroupAssignment(assignmentModel);

    assertNotNull(result);
    assertEquals("assignment-1", result.getId());

    ArgumentCaptor<JGroupAssignment> captor = ArgumentCaptor.forClass(JGroupAssignment.class);

    verify(groupAssignmentRepository).save(captor.capture());

    JGroupAssignment savedEntity = captor.getValue();

    assertEquals("assignment-1", savedEntity.getId());
    assertTrue(savedEntity.getIsActive());
    assertNull(savedEntity.getStudent());
    assertNull(savedEntity.getGroup());
    assertNull(savedEntity.getSemester());
  }

  @Test
  void shouldSaveGroupAssignmentWithAllRelations() {
    JStudent student = JStudent.builder().id("student-1").studentNumber("STD001").build();

    JGroup group = JGroup.builder().id("group-1").ref("G1").build();

    JSemester semester = JSemester.builder().id("semester-1").build();

    GroupAssignment model =
        GroupAssignment.builder()
            .id("assignment-1")
            .isActive(true)
            .student(com.example.demo.model.Student.builder().id("student-1").build())
            .group(com.example.demo.model.Group.builder().id("group-1").build())
            .semester(com.example.demo.model.Semester.builder().id("semester-1").build())
            .build();

    when(studentRepository.findById("student-1")).thenReturn(Optional.of(student));

    when(groupRepository.findById("group-1")).thenReturn(Optional.of(group));

    when(semesterRepository.findById("semester-1")).thenReturn(Optional.of(semester));

    when(groupAssignmentRepository.save(any(JGroupAssignment.class))).thenReturn(assignmentEntity);

    groupAssignmentService.saveGroupAssignment(model);

    ArgumentCaptor<JGroupAssignment> captor = ArgumentCaptor.forClass(JGroupAssignment.class);

    verify(groupAssignmentRepository).save(captor.capture());

    JGroupAssignment savedEntity = captor.getValue();

    assertEquals(student, savedEntity.getStudent());
    assertEquals(group, savedEntity.getGroup());
    assertEquals(semester, savedEntity.getSemester());
    assertTrue(savedEntity.getIsActive());

    verify(studentRepository).findById("student-1");
    verify(groupRepository).findById("group-1");
    verify(semesterRepository).findById("semester-1");
  }

  @Test
  void shouldThrowExceptionWhenStudentNotFound() {
    GroupAssignment model =
        GroupAssignment.builder()
            .id("assignment-1")
            .student(com.example.demo.model.Student.builder().id("unknown").build())
            .build();

    when(studentRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(
            RuntimeException.class, () -> groupAssignmentService.saveGroupAssignment(model));

    assertEquals("Student not found", exception.getMessage());

    verify(studentRepository).findById("unknown");
    verify(groupAssignmentRepository, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenGroupNotFound() {
    GroupAssignment model =
        GroupAssignment.builder()
            .id("assignment-1")
            .group(com.example.demo.model.Group.builder().id("unknown").build())
            .build();

    when(groupRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(
            RuntimeException.class, () -> groupAssignmentService.saveGroupAssignment(model));

    assertEquals("Group not found", exception.getMessage());

    verify(groupRepository).findById("unknown");
    verify(groupAssignmentRepository, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenSemesterNotFound() {
    GroupAssignment model =
        GroupAssignment.builder()
            .id("assignment-1")
            .semester(com.example.demo.model.Semester.builder().id("unknown").build())
            .build();

    when(semesterRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(
            RuntimeException.class, () -> groupAssignmentService.saveGroupAssignment(model));

    assertEquals("Semester not found", exception.getMessage());

    verify(semesterRepository).findById("unknown");
    verify(groupAssignmentRepository, never()).save(any());
  }
}
