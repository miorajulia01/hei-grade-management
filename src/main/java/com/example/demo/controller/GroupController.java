package com.example.demo.controller;

import com.example.demo.model.Group;
import com.example.demo.service.GroupService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

  private final GroupService groupService;

  @GetMapping
  public List<Group> getAll() {
    return groupService.getAllGroups();
  }

  @GetMapping("/{id}")
  public Group getById(@PathVariable String id) {
    return groupService.getGroupById(id);
  }

  @PostMapping
  public Group create(@RequestBody Group group) {
    return groupService.saveGroup(group);
  }

  @PutMapping("/{id}")
  public Group update(@PathVariable String id, @RequestBody Group group) {
    return groupService.updateGroup(id, group);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    groupService.deleteGroup(id);
    return ResponseEntity.noContent().build();
  }
}
