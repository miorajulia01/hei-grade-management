package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JDocExport;
import com.example.demo.entity.JUser;
import com.example.demo.model.DocExport;
import com.example.demo.model.User;
import com.example.demo.repository.DocExportRepository;
import com.example.demo.repository.UserRepository;
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
class DocExportServiceTest {

  @Mock private DocExportRepository docExportRepository;

  @Mock private UserRepository userRepository;

  @InjectMocks private DocExportService docExportService;

  private JUser userEntity;
  private User userModel;

  private JDocExport docExportEntity;
  private DocExport docExportModel;

  @BeforeEach
  void setUp() {

    userEntity = JUser.builder().id("user-1").email("test@example.com").build();

    userModel = User.builder().id("user-1").email("test@example.com").build();

    docExportEntity =
        JDocExport.builder()
            .id("export-1")
            .user(userEntity)
            .exportType("STUDENT_GRADES")
            .fileName("grades.pdf")
            .filePath("/exports/grades.pdf")
            .fileSize(1024L)
            .format("PDF")
            .filters("studentId=student-1")
            .build();

    docExportModel =
        DocExport.builder()
            .id("export-1")
            .user(userModel)
            .exportType("STUDENT_GRADES")
            .fileName("grades.pdf")
            .filePath("/exports/grades.pdf")
            .fileSize(1024L)
            .format("PDF")
            .filters("studentId=student-1")
            .build();
  }

  @Test
  void shouldGetAllDocExports() {
    when(docExportRepository.findAll()).thenReturn(List.of(docExportEntity));

    List<DocExport> result = docExportService.getAllDocExports();

    assertNotNull(result);
    assertEquals(1, result.size());

    assertEquals("export-1", result.get(0).getId());
    assertEquals("STUDENT_GRADES", result.get(0).getExportType());
    assertEquals("grades.pdf", result.get(0).getFileName());
    assertEquals("/exports/grades.pdf", result.get(0).getFilePath());
    assertEquals(1024L, result.get(0).getFileSize());
    assertEquals("PDF", result.get(0).getFormat());
    assertEquals("studentId=student-1", result.get(0).getFilters());

    verify(docExportRepository).findAll();
  }

  @Test
  void shouldReturnEmptyListWhenNoDocExportsExist() {
    when(docExportRepository.findAll()).thenReturn(List.of());

    List<DocExport> result = docExportService.getAllDocExports();

    assertNotNull(result);
    assertTrue(result.isEmpty());

    verify(docExportRepository).findAll();
  }

  @Test
  void shouldGetDocExportById() {
    when(docExportRepository.findById("export-1")).thenReturn(Optional.of(docExportEntity));

    DocExport result = docExportService.getDocExportById("export-1");

    assertNotNull(result);

    assertEquals("export-1", result.getId());
    assertEquals("STUDENT_GRADES", result.getExportType());
    assertEquals("grades.pdf", result.getFileName());
    assertEquals("/exports/grades.pdf", result.getFilePath());
    assertEquals(1024L, result.getFileSize());
    assertEquals("PDF", result.getFormat());
    assertEquals("studentId=student-1", result.getFilters());

    verify(docExportRepository).findById("export-1");
  }

  @Test
  void shouldThrowExceptionWhenDocExportNotFound() {
    when(docExportRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> docExportService.getDocExportById("unknown"));

    assertEquals("DocExport not found with id: unknown", exception.getMessage());

    verify(docExportRepository).findById("unknown");
  }

  @Test
  void shouldSaveDocExportWithUser() {
    when(userRepository.findById("user-1")).thenReturn(Optional.of(userEntity));

    when(docExportRepository.save(any(JDocExport.class))).thenReturn(docExportEntity);

    DocExport result = docExportService.saveDocExport(docExportModel);

    assertNotNull(result);

    assertEquals("export-1", result.getId());
    assertEquals("STUDENT_GRADES", result.getExportType());
    assertEquals("grades.pdf", result.getFileName());
    assertEquals("/exports/grades.pdf", result.getFilePath());
    assertEquals(1024L, result.getFileSize());
    assertEquals("PDF", result.getFormat());
    assertEquals("studentId=student-1", result.getFilters());

    ArgumentCaptor<JDocExport> captor = ArgumentCaptor.forClass(JDocExport.class);

    verify(docExportRepository).save(captor.capture());

    JDocExport savedEntity = captor.getValue();

    assertEquals("export-1", savedEntity.getId());
    assertEquals(userEntity, savedEntity.getUser());
    assertEquals("STUDENT_GRADES", savedEntity.getExportType());
    assertEquals("grades.pdf", savedEntity.getFileName());
    assertEquals("/exports/grades.pdf", savedEntity.getFilePath());
    assertEquals(1024L, savedEntity.getFileSize());
    assertEquals("PDF", savedEntity.getFormat());
    assertEquals("studentId=student-1", savedEntity.getFilters());

    verify(userRepository).findById("user-1");
  }

  @Test
  void shouldSaveDocExportWithoutUser() {
    DocExport model =
        DocExport.builder()
            .id("export-2")
            .user(null)
            .exportType("COURSES")
            .fileName("courses.csv")
            .filePath("/exports/courses.csv")
            .fileSize(2048L)
            .format("CSV")
            .filters(null)
            .build();

    JDocExport savedEntity =
        JDocExport.builder()
            .id("export-2")
            .user(null)
            .exportType("COURSES")
            .fileName("courses.csv")
            .filePath("/exports/courses.csv")
            .fileSize(2048L)
            .format("CSV")
            .filters(null)
            .build();

    when(docExportRepository.save(any(JDocExport.class))).thenReturn(savedEntity);

    DocExport result = docExportService.saveDocExport(model);

    assertNotNull(result);

    assertEquals("export-2", result.getId());
    assertEquals("COURSES", result.getExportType());
    assertEquals("courses.csv", result.getFileName());
    assertEquals("CSV", result.getFormat());

    ArgumentCaptor<JDocExport> captor = ArgumentCaptor.forClass(JDocExport.class);

    verify(docExportRepository).save(captor.capture());

    JDocExport entity = captor.getValue();

    assertEquals("export-2", entity.getId());
    assertNull(entity.getUser());
    assertEquals("COURSES", entity.getExportType());
    assertEquals("courses.csv", entity.getFileName());
    assertEquals("/exports/courses.csv", entity.getFilePath());
    assertEquals(2048L, entity.getFileSize());
    assertEquals("CSV", entity.getFormat());
    assertNull(entity.getFilters());

    verifyNoInteractions(userRepository);
  }

  @Test
  void shouldThrowExceptionWhenUserNotFound() {
    DocExport model =
        DocExport.builder()
            .id("export-1")
            .user(User.builder().id("unknown").build())
            .exportType("STUDENT_GRADES")
            .fileName("grades.pdf")
            .filePath("/exports/grades.pdf")
            .fileSize(1024L)
            .format("PDF")
            .filters("studentId=student-1")
            .build();

    when(userRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> docExportService.saveDocExport(model));

    assertEquals("User not found", exception.getMessage());

    verify(userRepository).findById("unknown");
    verify(docExportRepository, never()).save(any());
  }

  @Test
  void shouldSaveDocExportWhenUserHasNullId() {
    DocExport model =
        DocExport.builder()
            .id("export-3")
            .user(User.builder().id(null).build())
            .exportType("MOVIES")
            .fileName("movies.json")
            .filePath("/exports/movies.json")
            .fileSize(512L)
            .format("JSON")
            .filters("active=true")
            .build();

    JDocExport savedEntity =
        JDocExport.builder()
            .id("export-3")
            .user(null)
            .exportType("MOVIES")
            .fileName("movies.json")
            .filePath("/exports/movies.json")
            .fileSize(512L)
            .format("JSON")
            .filters("active=true")
            .build();

    when(docExportRepository.save(any(JDocExport.class))).thenReturn(savedEntity);

    DocExport result = docExportService.saveDocExport(model);

    assertNotNull(result);
    assertEquals("export-3", result.getId());
    assertEquals("MOVIES", result.getExportType());

    ArgumentCaptor<JDocExport> captor = ArgumentCaptor.forClass(JDocExport.class);

    verify(docExportRepository).save(captor.capture());

    JDocExport entity = captor.getValue();

    assertNull(entity.getUser());
    assertEquals("MOVIES", entity.getExportType());

    verifyNoInteractions(userRepository);
  }

  @Test
  void shouldUpdateDocExport() {
    when(docExportRepository.findById("export-1")).thenReturn(Optional.of(docExportEntity));

    DocExport model =
        DocExport.builder()
            .id("export-1")
            .exportType("TRANSCRIPT")
            .fileName("transcript.pdf")
            .filePath("/exports/transcript.pdf")
            .fileSize(2048L)
            .format("PDF")
            .filters("promotionId=promo-1")
            .build();

    JDocExport updatedEntity =
        JDocExport.builder()
            .id("export-1")
            .user(userEntity)
            .exportType("TRANSCRIPT")
            .fileName("transcript.pdf")
            .filePath("/exports/transcript.pdf")
            .fileSize(2048L)
            .format("PDF")
            .filters("promotionId=promo-1")
            .build();

    when(docExportRepository.save(any(JDocExport.class))).thenReturn(updatedEntity);

    DocExport result = docExportService.updateDocExport("export-1", model);

    assertNotNull(result);
    assertEquals("TRANSCRIPT", result.getExportType());
    assertEquals("transcript.pdf", result.getFileName());
    assertEquals("/exports/transcript.pdf", result.getFilePath());
    assertEquals(2048L, result.getFileSize());
    assertEquals("PDF", result.getFormat());
    assertEquals("promotionId=promo-1", result.getFilters());

    ArgumentCaptor<JDocExport> captor = ArgumentCaptor.forClass(JDocExport.class);

    verify(docExportRepository).save(captor.capture());

    JDocExport savedEntity = captor.getValue();

    assertEquals(userEntity, savedEntity.getUser());
    assertEquals("TRANSCRIPT", savedEntity.getExportType());
    assertEquals("transcript.pdf", savedEntity.getFileName());
    assertEquals("promotionId=promo-1", savedEntity.getFilters());

    verify(docExportRepository).findById("export-1");
  }

  @Test
  void shouldUpdateDocExportWithUser() {
    when(docExportRepository.findById("export-1")).thenReturn(Optional.of(docExportEntity));

    JUser newUser = JUser.builder().id("user-2").email("new@example.com").build();

    DocExport model =
        DocExport.builder()
            .id("export-1")
            .user(User.builder().id("user-2").build())
            .exportType("GRADUATE_LIST")
            .fileName("graduates.pdf")
            .filePath("/exports/graduates.pdf")
            .fileSize(4096L)
            .format("PDF")
            .filters("promotionId=promo-1")
            .build();

    when(userRepository.findById("user-2")).thenReturn(Optional.of(newUser));

    when(docExportRepository.save(any(JDocExport.class))).thenReturn(docExportEntity);

    docExportService.updateDocExport("export-1", model);

    ArgumentCaptor<JDocExport> captor = ArgumentCaptor.forClass(JDocExport.class);

    verify(docExportRepository).save(captor.capture());

    assertEquals(newUser, captor.getValue().getUser());

    verify(userRepository).findById("user-2");
  }

  @Test
  void shouldThrowExceptionWhenUpdatingDocExportNotFound() {
    when(docExportRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> docExportService.updateDocExport("unknown", DocExport.builder().build()));

    assertEquals("DocExport not found with id: unknown", exception.getMessage());

    verify(docExportRepository).findById("unknown");
    verify(docExportRepository, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenUpdatingWithUserNotFound() {
    when(docExportRepository.findById("export-1")).thenReturn(Optional.of(docExportEntity));

    DocExport model =
        DocExport.builder()
            .id("export-1")
            .user(User.builder().id("unknown").build())
            .exportType("TRANSCRIPT")
            .fileName("transcript.pdf")
            .filePath("/exports/transcript.pdf")
            .fileSize(2048L)
            .format("PDF")
            .filters(null)
            .build();

    when(userRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(
            RuntimeException.class, () -> docExportService.updateDocExport("export-1", model));

    assertEquals("User not found", exception.getMessage());

    verify(docExportRepository).findById("export-1");
    verify(userRepository).findById("unknown");
    verify(docExportRepository, never()).save(any());
  }

  @Test
  void shouldDeleteDocExport() {
    when(docExportRepository.existsById("export-1")).thenReturn(true);

    docExportService.deleteDocExport("export-1");

    verify(docExportRepository).deleteById("export-1");
  }

  @Test
  void shouldThrowExceptionWhenDeletingDocExportNotFound() {
    when(docExportRepository.existsById("unknown")).thenReturn(false);

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> docExportService.deleteDocExport("unknown"));

    assertEquals("DocExport not found with id: unknown", exception.getMessage());

    verify(docExportRepository, never()).deleteById("unknown");
  }
}
