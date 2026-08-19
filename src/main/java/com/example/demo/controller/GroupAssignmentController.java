package com.example.demo.controller;

import com.example.demo.model.GroupAssignment;
import com.example.demo.service.GroupAssignmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-assignments")
@RequiredArgsConstructor
public class GroupAssignmentController {

  private final GroupAssignmentService groupAssignmentService;

  @GetMapping
  public List<GroupAssignment> getAll() {
    return groupAssignmentService.getAllGroupAssignments();
  }

  @GetMapping("/{id}")
  public GroupAssignment getById(@PathVariable String id) {
    return groupAssignmentService.getGroupAssignmentById(id);
  }

  @PostMapping
  public GroupAssignment create(@RequestBody GroupAssignment groupAssignment) {
    return groupAssignmentService.saveGroupAssignment(groupAssignment);
  }

  @PutMapping("/{id}")
  public GroupAssignment update(
      @PathVariable String id, @RequestBody GroupAssignment groupAssignment) {
    return groupAssignmentService.updateGroupAssignment(id, groupAssignment);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    groupAssignmentService.deleteGroupAssignment(id);
    return ResponseEntity.noContent().build();
  }
}
