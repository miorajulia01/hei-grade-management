package com.example.demo.service;

import com.example.demo.entity.JAcademicYear;
import com.example.demo.mapper.AcademicYearMapper;
import com.example.demo.model.AcademicYear;
import com.example.demo.repository.AcademicYearRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcademicYearService {

  private final AcademicYearRepository academicYearRepository;

  public List<AcademicYear> getAllAcademicYears() {
    return academicYearRepository.findAll().stream().map(AcademicYearMapper::toModel).toList();
  }

  public AcademicYear getAcademicYearById(String id) {
    JAcademicYear entity =
        academicYearRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Academic year not found with id: " + id));
    return AcademicYearMapper.toModel(entity);
  }

  public AcademicYear saveAcademicYear(AcademicYear model) {
    JAcademicYear entity =
        JAcademicYear.builder()
            .id(model.getId())
            .label(model.getLabel())
            .startDate(model.getStartDate())
            .endDate(model.getEndDate())
            .build();
    JAcademicYear saved = academicYearRepository.save(entity);
    return AcademicYearMapper.toModel(saved);
  }

  public AcademicYear updateAcademicYear(String id, AcademicYear model) {
    JAcademicYear existing =
        academicYearRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Academic year not found with id: " + id));
    existing.setLabel(model.getLabel());
    existing.setStartDate(model.getStartDate());
    existing.setEndDate(model.getEndDate());
    JAcademicYear saved = academicYearRepository.save(existing);
    return AcademicYearMapper.toModel(saved);
  }

  public void deleteAcademicYear(String id) {
    if (!academicYearRepository.existsById(id)) {
      throw new RuntimeException("Academic year not found with id: " + id);
    }
    academicYearRepository.deleteById(id);
  }
}
