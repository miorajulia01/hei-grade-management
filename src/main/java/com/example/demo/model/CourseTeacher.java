package com.example.demo.model;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseTeacher {
    private CourseTeacherId id;
    private Course course;
    private Teacher teacher;
    private LocalDate assignedAt;
    private Boolean isPrimary;
}