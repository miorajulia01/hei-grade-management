package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.AccessControlService;
import com.example.demo.service.StudentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final AccessControlService accessControlService;

    @GetMapping
    public List<Student> getAll() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable String id) {
        accessControlService.assertOwnStudentOrStaff(id);
        return studentService.getStudentById(id);
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }
}