package com.example.demo.repository;

import com.example.demo.entity.JDocExport;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocExportRepository extends JpaRepository<JDocExport, String> {
  List<JDocExport> findByUserId(String userId);

  List<JDocExport> findByExportType(String exportType);

  List<JDocExport> findByFormat(String format);
}
