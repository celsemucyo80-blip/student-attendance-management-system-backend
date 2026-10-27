package com.auca.student_attendance_management_system.repository;

import com.auca.student_attendance_management_system.model.Classroom;
import com.auca.student_attendance_management_system.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    @Query("SELECT DISTINCT t FROM Teacher t JOIN t.subjects s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Teacher> findTeachersBySubjectName(@Param("name") String name);

    boolean existsByClassroom(Classroom classroom);
}