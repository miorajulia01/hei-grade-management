package com.example.demo.service;

import com.example.demo.entity.JStudent;
import com.example.demo.entity.JTeacher;
import com.example.demo.entity.JUser;
import com.example.demo.repository.CourseTeacherRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccessControlService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseTeacherRepository courseTeacherRepository;

    public JUser getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new AccessDeniedException("Utilisateur authentifie introuvable."));
    }

    public boolean isAdmin() {
        return hasRole("ADMIN");
    }

    public boolean isTeacher() {
        return hasRole("TEACHER");
    }

    private boolean hasRole(String role) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

    public void assertOwnStudentOrStaff(String studentId) {
        if (isAdmin() || isTeacher()) {
            return;
        }
        JUser user = getCurrentUser();
        JStudent student = studentRepository.findByUserId(user.getId()).orElse(null);
        if (student == null || !student.getId().equals(studentId)) {
            throw new AccessDeniedException("Vous ne pouvez consulter que vos propres donnees.");
        }
    }

    public void assertTeachesCourseOrAdmin(String courseId) {
        if (isAdmin()) {
            return;
        }
        JUser user = getCurrentUser();
        JTeacher teacher =
                teacherRepository
                        .findByUserId(user.getId())
                        .orElseThrow(
                                () -> new AccessDeniedException("Seuls les enseignants et administrateurs peuvent faire ceci."));

        boolean teachesThisCourse =
                courseTeacherRepository.findByTeacherId(teacher.getId()).stream()
                        .anyMatch(ct -> ct.getCourse().getId().equals(courseId));

        if (!teachesThisCourse) {
            throw new AccessDeniedException("Vous n'enseignez pas ce cours.");
        }
    }
}