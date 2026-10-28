package com.auca.student_attendance_management_system.service;

import com.auca.student_attendance_management_system.model.LeaveRequest;
import com.auca.student_attendance_management_system.model.LeaveStatus;
import com.auca.student_attendance_management_system.repository.CatchUpTaskRepository;
import com.auca.student_attendance_management_system.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private CatchUpTaskRepository catchUpTaskRepository;

    public String saveLeaveRequest(LeaveRequest leaveRequest) {
        if (leaveRequest.getLeaveDate().isBefore(LocalDate.now())) {
            return "Leave request date cannot be in the past";
        }
        if (leaveRequest.getTeacher() != null) {
            boolean busy = leaveRequestRepository.existsByTeacherIdAndLeaveDateAndStatusNot(
                    leaveRequest.getTeacher().getId(), leaveRequest.getLeaveDate(), LeaveStatus.DENIED);
            if (busy) {
                return "Teacher already handles a leave request on that date";
            }
        }
        leaveRequest.setStatus(LeaveStatus.REQUESTED);
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
        Optional<LeaveRequest> found = leaveRequestRepository.findById(id);
        if (found.isEmpty()) {
            return "The leave request with that id does not exist";
        }
        if (catchUpTaskRepository.existsByLeaveRequest(found.get())) {
            return "That leave request has catch-up tasks, delete them first";
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

    public String updateStatus(UUID id, LeaveStatus newStatus) {
        Optional<LeaveRequest> found = leaveRequestRepository.findById(id);
        if (found.isEmpty()) {
            return "The leave request with that id does not exist";
        }
        LeaveRequest leaveRequest = found.get();
        LeaveStatus current = leaveRequest.getStatus();

        boolean allowed = false;
        if (current == LeaveStatus.REQUESTED) {
            if (newStatus == LeaveStatus.REVIEWED || newStatus == LeaveStatus.DENIED) {
                allowed = true;
            }
        } else if (current == LeaveStatus.REVIEWED) {
            if (newStatus == LeaveStatus.GRANTED || newStatus == LeaveStatus.DENIED) {
                allowed = true;
            }
        }

        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        leaveRequest.setStatus(newStatus);
        leaveRequestRepository.save(leaveRequest);
        return "Leave request status updated successfully";
    }

    public List<Object[]> getStatusStats() {
        return leaveRequestRepository.getStatusStats();
    }

    @Transactional
    public String cancelDay(UUID teacherId, LocalDate date) {
        int count = leaveRequestRepository.cancelDay(teacherId, date, LeaveStatus.DENIED, LeaveStatus.GRANTED);
        return count + " leave requests cancelled";
    }
}