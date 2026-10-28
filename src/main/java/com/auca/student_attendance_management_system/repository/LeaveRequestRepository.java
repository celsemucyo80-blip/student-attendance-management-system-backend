package com.auca.student_attendance_management_system.repository;

import com.auca.student_attendance_management_system.model.LeaveRequest;
import com.auca.student_attendance_management_system.model.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, UUID> {

    List<LeaveRequest> findByStatusOrderByLeaveDateAsc(LeaveStatus status);

    List<LeaveRequest> findByLeaveDateBetweenOrderByLeaveDateAsc(LocalDate start, LocalDate end);

    boolean existsByTeacherIdAndLeaveDateAndStatusNot(UUID teacherId, LocalDate leaveDate, LeaveStatus status);

    @Query("SELECT lr.status, COUNT(lr) FROM LeaveRequest lr GROUP BY lr.status")
    List<Object[]> getStatusStats();

    @Modifying
    @Query("UPDATE LeaveRequest lr SET lr.status = :denied WHERE lr.teacher.id = :teacherId AND lr.leaveDate = :date AND lr.status <> :granted")
    int cancelDay(@Param("teacherId") UUID teacherId,
                  @Param("date") LocalDate date,
                  @Param("denied") LeaveStatus denied,
                  @Param("granted") LeaveStatus granted);
}