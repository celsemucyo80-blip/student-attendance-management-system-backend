package com.auca.student_attendance_management_system.repository;

import com.auca.student_attendance_management_system.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {

    @Query("SELECT c FROM Classroom c WHERE NOT EXISTS (SELECT t FROM Teacher t WHERE t.classroom = c) ORDER BY c.roomNumber")
    List<Classroom> findFreeClassrooms();

    Optional<Classroom> findByRoomNumber(int roomNumber);
}