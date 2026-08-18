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
}
