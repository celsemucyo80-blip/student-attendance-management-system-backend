package com.auca.student_attendance_management_system.service;

import com.auca.student_attendance_management_system.model.LeaveRequest;
import com.auca.student_attendance_management_system.model.LeaveStatus;
import com.auca.student_attendance_management_system.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public String saveLeaveRequest(LeaveRequest leaveRequest) {
        leaveRequestRepository.save(leaveRequest);
        return "Leave request saved successfully";
    }

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    public Optional<LeaveRequest> getLeaveRequestById(UUID id) {
        return leaveRequestRepository.findById(id);
    }

    public String updateLeaveRequest(UUID id, LeaveRequest newData) {
        Optional<LeaveRequest> found = leaveRequestRepository.findById(id);
        if (found.isEmpty()) {
            return "The leave request with that id does not exist";
        }
        LeaveRequest leaveRequest = found.get();
        leaveRequest.setLeaveDate(newData.getLeaveDate());
        leaveRequest.setReason(newData.getReason());
        if (newData.getStudent() != null) {
            leaveRequest.setStudent(newData.getStudent());
        }
        if (newData.getTeacher() != null) {
            leaveRequest.setTeacher(newData.getTeacher());
        }
        leaveRequestRepository.save(leaveRequest);
        return "Leave request updated successfully";
    }

    public String deleteLeaveRequest(UUID id) {
        if (!leaveRequestRepository.existsById(id)) {
            return "The leave request with that id does not exist";
        }
        leaveRequestRepository.deleteById(id);
        return "Leave request deleted successfully";
    }

    public List<LeaveRequest> getLeaveRequestsByStatus(LeaveStatus status) {
        return leaveRequestRepository.findByStatusOrderByLeaveDateAsc(status);
    }

    public List<LeaveRequest> getLeaveRequestsBetween(LocalDate start, LocalDate end) {
        return leaveRequestRepository.findByLeaveDateBetweenOrderByLeaveDateAsc(start, end);
    }
}