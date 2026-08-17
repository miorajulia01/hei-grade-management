package com.example.demo.entity;

import com.example.demo.enums.StatusEnum;
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

    @Column(name = "doc_type", nullable = false)
    private String docType;

    @Column(name = "file_url")
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "created_at")
    private Instant createdAt;
}