package com.example.demo.model;

import com.example.demo.enums.StatusEnum;
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
    private String docType;
    private String fileUrl;
    private StatusEnum status;
    private Instant createdAt;
}