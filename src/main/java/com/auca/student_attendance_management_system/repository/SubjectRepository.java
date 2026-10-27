package com.auca.student_attendance_management_system.repository;

import com.auca.student_attendance_management_system.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {

    @Query("SELECT s FROM Subject s WHERE s.teachers IS EMPTY")
    List<Subject> findUnusedSubjects();
}