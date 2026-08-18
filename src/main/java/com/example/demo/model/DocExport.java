package com.example.demo.model;

import java.time.Instant;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocExport {
  private String id;
  private User user;
  private String exportType;
  private String fileName;
  private String filePath;
  private Long fileSize;
  private String format;
  private String filters;
  private Instant createdAt;
}
