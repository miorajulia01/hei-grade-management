package com.example.demo.service;

import com.example.demo.mapper.GradeHistoryMapper;
import com.example.demo.model.GradeHistory;
import com.example.demo.repository.GradeHistoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GradeHistoryService {

  private final GradeHistoryRepository gradeHistoryRepository;
  private final GradeHistoryMapper gradeHistoryMapper;

  public GradeHistoryService(
      GradeHistoryRepository gradeHistoryRepository, GradeHistoryMapper gradeHistoryMapper) {
    this.gradeHistoryRepository = gradeHistoryRepository;
    this.gradeHistoryMapper = gradeHistoryMapper;
  }

  @Transactional(readOnly = true)
  public List<GradeHistory> getHistoryByGradeId(String gradeId) {
    if (gradeId == null || gradeId.isBlank()) {
      throw new IllegalArgumentException("Grade ID must be provided to fetch history.");
    }

    return gradeHistoryRepository.findByGradeIdOrderByUpdatedAtDesc(gradeId).stream()
        .map(gradeHistoryMapper::toModel)
        .collect(Collectors.toList());
  }
}
