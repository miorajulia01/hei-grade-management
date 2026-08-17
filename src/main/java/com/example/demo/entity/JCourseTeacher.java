package com.example.demo.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "course_teacher")
@IdClass(JCourseTeacher.JCourseTeacherId.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JCourseTeacher {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private JCourse course;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private JTeacher teacher;

    @Column(name = "assigned_at")
    private LocalDate assignedAt;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class JCourseTeacherId implements Serializable {
        private String course;
        private String teacher;
    }
}