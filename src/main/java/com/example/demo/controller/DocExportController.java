package com.example.demo.controller;

import com.example.demo.model.DocExport;
import com.example.demo.service.DocExportService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doc-exports")
@RequiredArgsConstructor
public class DocExportController {

  private final DocExportService docExportService;

  @GetMapping
  public List<DocExport> getAll() {
    return docExportService.getAllDocExports();
  }

  @GetMapping("/{id}")
  public DocExport getById(@PathVariable String id) {
    return docExportService.getDocExportById(id);
  }

  @PostMapping
  public DocExport create(@RequestBody DocExport docExport) {
    return docExportService.saveDocExport(docExport);
  }

  @PutMapping("/{id}")
  public DocExport update(@PathVariable String id, @RequestBody DocExport docExport) {
    return docExportService.updateDocExport(id, docExport);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    docExportService.deleteDocExport(id);
    return ResponseEntity.noContent().build();
  }
}
