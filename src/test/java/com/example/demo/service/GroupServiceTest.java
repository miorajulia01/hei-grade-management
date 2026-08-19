package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JGroup;
import com.example.demo.entity.JProgram;
import com.example.demo.model.Group;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.ProgramRepository;
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
class GroupServiceTest {

  @Mock private GroupRepository groupRepository;

  @Mock private ProgramRepository programRepository;

  @InjectMocks private GroupService groupService;

  private JGroup groupEntity;
  private Group groupModel;

  @BeforeEach
  void setUp() {
    groupEntity = JGroup.builder().id("group-1").ref("G1").build();

    groupModel = Group.builder().id("group-1").ref("G1").build();
  }

  @Test
  void shouldGetAllGroups() {
    when(groupRepository.findAll()).thenReturn(List.of(groupEntity));

    List<Group> result = groupService.getAllGroups();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("group-1", result.get(0).getId());
    assertEquals("G1", result.get(0).getRef());

    verify(groupRepository).findAll();
  }

  @Test
  void shouldGetGroupById() {
    when(groupRepository.findById("group-1")).thenReturn(Optional.of(groupEntity));

    Group result = groupService.getGroupById("group-1");

    assertNotNull(result);
    assertEquals("group-1", result.getId());
    assertEquals("G1", result.getRef());

    verify(groupRepository).findById("group-1");
  }

  @Test
  void shouldThrowExceptionWhenGroupNotFound() {
    when(groupRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> groupService.getGroupById("unknown"));

    assertEquals("Group not found with id: unknown", exception.getMessage());

    verify(groupRepository).findById("unknown");
  }

  @Test
  void shouldSaveGroupWithoutProgram() {
    when(groupRepository.save(any(JGroup.class))).thenReturn(groupEntity);

    Group result = groupService.saveGroup(groupModel);

    assertNotNull(result);
    assertEquals("group-1", result.getId());
    assertEquals("G1", result.getRef());

    ArgumentCaptor<JGroup> captor = ArgumentCaptor.forClass(JGroup.class);

    verify(groupRepository).save(captor.capture());

    JGroup savedEntity = captor.getValue();

    assertEquals("group-1", savedEntity.getId());
    assertEquals("G1", savedEntity.getRef());
    assertNull(savedEntity.getProgram());
  }

  @Test
  void shouldSaveGroupWithProgram() {
    JProgram program = JProgram.builder().id("program-1").build();

    Group model =
        Group.builder()
            .id("group-1")
            .ref("G1")
            .program(com.example.demo.model.Program.builder().id("program-1").build())
            .build();

    when(programRepository.findById("program-1")).thenReturn(Optional.of(program));

    when(groupRepository.save(any(JGroup.class))).thenReturn(groupEntity);

    groupService.saveGroup(model);

    ArgumentCaptor<JGroup> captor = ArgumentCaptor.forClass(JGroup.class);

    verify(groupRepository).save(captor.capture());

    assertEquals(program, captor.getValue().getProgram());
    verify(programRepository).findById("program-1");
  }

  @Test
  void shouldThrowExceptionWhenProgramNotFound() {
    Group model =
        Group.builder()
            .id("group-1")
            .ref("G1")
            .program(com.example.demo.model.Program.builder().id("unknown").build())
            .build();

    when(programRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> groupService.saveGroup(model));

    assertEquals("Program not found", exception.getMessage());

    verify(programRepository).findById("unknown");
    verify(groupRepository, never()).save(any());
  }

  @Test
  void shouldUpdateGroup() {
    when(groupRepository.findById("group-1")).thenReturn(Optional.of(groupEntity));

    Group model = Group.builder().id("group-1").ref("G2").capacity(30).build();

    JGroup updatedEntity = JGroup.builder().id("group-1").ref("G2").capacity(30).build();

    when(groupRepository.save(any(JGroup.class))).thenReturn(updatedEntity);

    Group result = groupService.updateGroup("group-1", model);

    assertNotNull(result);
    assertEquals("G2", result.getRef());
    assertEquals(30, result.getCapacity());

    verify(groupRepository).findById("group-1");
    verify(groupRepository).save(any(JGroup.class));
  }

  @Test
  void shouldUpdateGroupWithProgram() {
    when(groupRepository.findById("group-1")).thenReturn(Optional.of(groupEntity));

    JProgram program = JProgram.builder().id("program-1").build();

    Group model =
        Group.builder()
            .id("group-1")
            .ref("G1")
            .program(com.example.demo.model.Program.builder().id("program-1").build())
            .build();

    when(programRepository.findById("program-1")).thenReturn(Optional.of(program));

    when(groupRepository.save(any(JGroup.class))).thenReturn(groupEntity);

    groupService.updateGroup("group-1", model);

    ArgumentCaptor<JGroup> captor = ArgumentCaptor.forClass(JGroup.class);

    verify(groupRepository).save(captor.capture());

    assertEquals(program, captor.getValue().getProgram());
    verify(programRepository).findById("program-1");
  }

  @Test
  void shouldThrowExceptionWhenUpdatingGroupNotFound() {
    when(groupRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> groupService.updateGroup("unknown", Group.builder().build()));

    assertEquals("Group not found with id: unknown", exception.getMessage());

    verify(groupRepository).findById("unknown");
    verify(groupRepository, never()).save(any());
  }

  @Test
  void shouldDeleteGroup() {
    when(groupRepository.existsById("group-1")).thenReturn(true);

    groupService.deleteGroup("group-1");

    verify(groupRepository).deleteById("group-1");
  }

  @Test
  void shouldThrowExceptionWhenDeletingGroupNotFound() {
    when(groupRepository.existsById("unknown")).thenReturn(false);

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> groupService.deleteGroup("unknown"));

    assertEquals("Group not found with id: unknown", exception.getMessage());

    verify(groupRepository, never()).deleteById("unknown");
  }
}
