package com.auca.student_attendance_management_system.controller;

import com.auca.student_attendance_management_system.model.CatchUpTask;
import com.auca.student_attendance_management_system.service.CatchUpTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/catch-up-tasks")
public class CatchUpTaskController {

    @Autowired
    private CatchUpTaskService catchUpTaskService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID leaveRequestId, @RequestBody CatchUpTask task) {
        String message = catchUpTaskService.saveCatchUpTask(leaveRequestId, task);
        if (message.equals("The leave request with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.equals("Catch-up tasks are only for GRANTED leave requests")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
    }

    @GetMapping("/student/{regNumber}")
    public List<CatchUpTask> getTasksOfStudent(@PathVariable String regNumber) {
        return catchUpTaskService.getTasksOfStudent(regNumber);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = catchUpTaskService.deleteCatchUpTask(id);
        if (message.equals("The catch up task with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}