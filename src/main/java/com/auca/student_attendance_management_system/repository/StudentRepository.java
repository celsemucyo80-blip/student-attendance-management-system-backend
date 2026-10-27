package com.auca.student_attendance_management_system.repository;

import com.auca.student_attendance_management_system.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    List<Student> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT lr.student FROM LeaveRequest lr WHERE lr.teacher.id = :teacherId")
    List<Student> findStudentsOfTeacher(@Param("teacherId") UUID teacherId);
}