package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public List<User> getAll() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public User getById(@PathVariable String id) {
    return userService.getUserById(id);
  }

  @PostMapping
  public User create(@RequestBody User user) {
    return userService.saveUser(user);
  }

  @PutMapping("/{id}")
  public User update(@PathVariable String id, @RequestBody User user) {
    return userService.updateUser(id, user);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}
