package com.auca.student_attendance_management_system.repository;

import com.auca.student_attendance_management_system.model.LeaveRequest;
import com.auca.student_attendance_management_system.model.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, UUID> {

    List<LeaveRequest> findByStatusOrderByLeaveDateAsc(LeaveStatus status);

    List<LeaveRequest> findByLeaveDateBetweenOrderByLeaveDateAsc(LocalDate start, LocalDate end);

    boolean existsByTeacherIdAndLeaveDateAndStatusNot(UUID teacherId, LocalDate leaveDate, LeaveStatus status);
}