package com.example.demo.service;

import com.example.demo.entity.JDocExport;
import com.example.demo.entity.JUser;
import com.example.demo.mapper.DocExportMapper;
import com.example.demo.model.DocExport;
import com.example.demo.repository.DocExportRepository;
import com.example.demo.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocExportService {

  private final DocExportRepository docExportRepository;
  private final UserRepository userRepository;

  public List<DocExport> getAllDocExports() {
    return docExportRepository.findAll().stream().map(DocExportMapper::toModel).toList();
  }

  public DocExport getDocExportById(String id) {
    JDocExport entity =
        docExportRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("DocExport not found with id: " + id));
    return DocExportMapper.toModel(entity);
  }

  public DocExport saveDocExport(DocExport model) {
    JUser user = null;
    if (model.getUser() != null && model.getUser().getId() != null) {
      user =
          userRepository
              .findById(model.getUser().getId())
              .orElseThrow(() -> new RuntimeException("User not found"));
    }

    JDocExport entity =
        JDocExport.builder()
            .id(model.getId())
            .user(user)
            .exportType(model.getExportType())
            .fileName(model.getFileName())
            .filePath(model.getFilePath())
            .fileSize(model.getFileSize())
            .format(model.getFormat())
            .filters(model.getFilters())
            .createdAt(model.getCreatedAt())
            .build();

    JDocExport saved = docExportRepository.save(entity);
    return DocExportMapper.toModel(saved);
  }

  public DocExport updateDocExport(String id, DocExport model) {
    JDocExport existing =
        docExportRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("DocExport not found with id: " + id));

    if (model.getUser() != null && model.getUser().getId() != null) {
      JUser user =
          userRepository
              .findById(model.getUser().getId())
              .orElseThrow(() -> new RuntimeException("User not found"));
      existing.setUser(user);
    }
    existing.setExportType(model.getExportType());
    existing.setFileName(model.getFileName());
    existing.setFilePath(model.getFilePath());
    existing.setFileSize(model.getFileSize());
    existing.setFormat(model.getFormat());
    existing.setFilters(model.getFilters());

    JDocExport saved = docExportRepository.save(existing);
    return DocExportMapper.toModel(saved);
  }

  public void deleteDocExport(String id) {
    if (!docExportRepository.existsById(id)) {
      throw new RuntimeException("DocExport not found with id: " + id);
    }
    docExportRepository.deleteById(id);
  }
}
