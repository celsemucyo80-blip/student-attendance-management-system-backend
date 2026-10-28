package com.auca.student_attendance_management_system.service;

import com.auca.student_attendance_management_system.model.CatchUpTask;
import com.auca.student_attendance_management_system.model.LeaveRequest;
import com.auca.student_attendance_management_system.model.LeaveStatus;
import com.auca.student_attendance_management_system.repository.CatchUpTaskRepository;
import com.auca.student_attendance_management_system.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CatchUpTaskService {

    @Autowired
    private CatchUpTaskRepository catchUpTaskRepository;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public String saveCatchUpTask(UUID leaveRequestId, CatchUpTask task) {
        Optional<LeaveRequest> found = leaveRequestRepository.findById(leaveRequestId);
        if (found.isEmpty()) {
            return "The leave request with that id does not exist";
        }
        LeaveRequest leaveRequest = found.get();
        if (leaveRequest.getStatus() != LeaveStatus.GRANTED) {
            return "Catch-up tasks are only for GRANTED leave requests";
        }
        task.setAssignedDate(LocalDate.now());
        task.setLeaveRequest(leaveRequest);
        catchUpTaskRepository.save(task);
        return "Catch up task saved successfully";
    }

    public List<CatchUpTask> getTasksOfStudent(String regNumber) {
        return catchUpTaskRepository.findByStudentRegNumber(regNumber);
    }

    public String deleteCatchUpTask(UUID id) {
        if (!catchUpTaskRepository.existsById(id)) {
            return "The catch up task with that id does not exist";
        }
        catchUpTaskRepository.deleteById(id);
        return "Catch up task deleted successfully";
    }
}