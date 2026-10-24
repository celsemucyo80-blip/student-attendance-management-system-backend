package com.auca.student_attendance_management_system.repository;

import com.auca.student_attendance_management_system.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {
}