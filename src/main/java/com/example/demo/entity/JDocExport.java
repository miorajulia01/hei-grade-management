package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "doc_export")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JDocExport {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private JUser user;

  @Column(name = "export_type", nullable = false)
  private String exportType;

  @Column(name = "file_name", nullable = false)
  private String fileName;

  @Column(name = "file_path")
  private String filePath;

  @Column(name = "file_size")
  private Long fileSize;

  @Column(name = "format", nullable = false)
  private String format;

  @Column(name = "filters", columnDefinition = "jsonb")
  private String filters;

  @Column(name = "created_at")
  private Instant createdAt;
}
