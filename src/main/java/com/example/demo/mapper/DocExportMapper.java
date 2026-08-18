package com.example.demo.mapper;

import com.example.demo.entity.JDocExport;
import com.example.demo.model.DocExport;

public class DocExportMapper {
  public static DocExport toModel(JDocExport entity) {
    if (entity == null) return null;
    return DocExport.builder()
        .id(entity.getId())
        .user(UserMapper.toModel(entity.getUser()))
        .exportType(entity.getExportType())
        .fileName(entity.getFileName())
        .filePath(entity.getFilePath())
        .fileSize(entity.getFileSize())
        .format(entity.getFormat())
        .filters(entity.getFilters())
        .createdAt(entity.getCreatedAt())
        .build();
  }

  public static JDocExport toEntity(DocExport model) {
    if (model == null) return null;
    return JDocExport.builder()
        .id(model.getId())
        .user(UserMapper.toEntity(model.getUser()))
        .exportType(model.getExportType())
        .fileName(model.getFileName())
        .filePath(model.getFilePath())
        .fileSize(model.getFileSize())
        .format(model.getFormat())
        .filters(model.getFilters())
        .createdAt(model.getCreatedAt())
        .build();
  }
}
