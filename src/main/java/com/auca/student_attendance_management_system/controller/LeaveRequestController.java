package com.auca.student_attendance_management_system.controller;

import com.auca.student_attendance_management_system.model.LeaveRequest;
import com.auca.student_attendance_management_system.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody LeaveRequest leaveRequest) {
        String message = leaveRequestService.saveLeaveRequest(leaveRequest);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<LeaveRequest> getAll() {
        return leaveRequestService.getAllLeaveRequests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<LeaveRequest> found = leaveRequestService.getLeaveRequestById(id);
        if (found.isPresent()) {
            return new ResponseEntity<>(found.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The leave request with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody LeaveRequest leaveRequest) {
        String message = leaveRequestService.updateLeaveRequest(id, leaveRequest);
        if (message.equals("The leave request with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = leaveRequestService.deleteLeaveRequest(id);
        if (message.equals("The leave request with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}