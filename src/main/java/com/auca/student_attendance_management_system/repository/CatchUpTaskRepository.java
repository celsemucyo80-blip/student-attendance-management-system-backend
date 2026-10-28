package com.auca.student_attendance_management_system.repository;

import com.auca.student_attendance_management_system.model.CatchUpTask;
import com.auca.student_attendance_management_system.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface CatchUpTaskRepository extends JpaRepository<CatchUpTask, UUID> {

    @Query("SELECT d FROM CatchUpTask d WHERE d.leaveRequest.student.regNumber = :regNumber ORDER BY d.assignedDate DESC")
    List<CatchUpTask> findByStudentRegNumber(@Param("regNumber") String regNumber);

    boolean existsByLeaveRequest(LeaveRequest leaveRequest);
}