package com.example.demo.service;

import com.example.demo.entity.JAcademicYear;
import com.example.demo.entity.JSemester;
import com.example.demo.mapper.SemesterMapper;
import com.example.demo.model.Semester;
import com.example.demo.repository.AcademicYearRepository;
import com.example.demo.repository.SemesterRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SemesterService {

  private final SemesterRepository semesterRepository;
  private final AcademicYearRepository academicYearRepository;

  public List<Semester> getAllSemesters() {
    return semesterRepository.findAll().stream().map(SemesterMapper::toModel).toList();
  }

  public Semester getSemesterById(String id) {
    JSemester entity =
        semesterRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Semester not found with id: " + id));
    return SemesterMapper.toModel(entity);
  }

  public Semester saveSemester(Semester model) {
    JAcademicYear academicYear = null;
    if (model.getAcademicYear() != null && model.getAcademicYear().getId() != null) {
      academicYear =
          academicYearRepository
              .findById(model.getAcademicYear().getId())
              .orElseThrow(() -> new RuntimeException("Academic year not found"));
    }

    JSemester entity =
        JSemester.builder()
            .id(model.getId())
            .academicYear(academicYear)
            .code(model.getCode())
            .label(model.getLabel())
            .order(model.getOrder())
            .startDate(model.getStartDate())
            .endDate(model.getEndDate())
            .build();

    JSemester saved = semesterRepository.save(entity);
    return SemesterMapper.toModel(saved);
  }

  public Semester updateSemester(String id, Semester model) {
    JSemester existing =
        semesterRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Semester not found with id: " + id));

    if (model.getAcademicYear() != null && model.getAcademicYear().getId() != null) {
      JAcademicYear academicYear =
          academicYearRepository
              .findById(model.getAcademicYear().getId())
              .orElseThrow(() -> new RuntimeException("Academic year not found"));
      existing.setAcademicYear(academicYear);
    }
    existing.setCode(model.getCode());
    existing.setLabel(model.getLabel());
    existing.setOrder(model.getOrder());
    existing.setStartDate(model.getStartDate());
    existing.setEndDate(model.getEndDate());

    JSemester saved = semesterRepository.save(existing);
    return SemesterMapper.toModel(saved);
  }

  public void deleteSemester(String id) {
    if (!semesterRepository.existsById(id)) {
      throw new RuntimeException("Semester not found with id: " + id);
    }
    semesterRepository.deleteById(id);
  }
}
