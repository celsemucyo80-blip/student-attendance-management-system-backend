package com.auca.student_attendance_management_system.controller;

import com.auca.student_attendance_management_system.model.Teacher;
import com.auca.student_attendance_management_system.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Teacher teacher) {
        String message = teacherService.saveTeacher(teacher);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<Teacher> getAll() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/by-subject")
    public List<Teacher> getBySubject(@RequestParam String name) {
        return teacherService.getTeachersBySubject(name);
    }

    @PutMapping("/{teacherId}/subjects/{subjectId}")
    public ResponseEntity<String> addSubject(@PathVariable UUID teacherId, @PathVariable UUID subjectId) {
        String message = teacherService.addSubjectToTeacher(teacherId, subjectId);
        if (message.equals("The teacher with that id does not exist")
                || message.equals("That subject does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.equals("The teacher already has that subject")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @PatchMapping("/{teacherId}/classroom")
    public ResponseEntity<String> changeClassroom(@PathVariable UUID teacherId, @RequestParam int roomNumber) {
        String message = teacherService.changeClassroom(teacherId, roomNumber);
        if (message.equals("The teacher with that id does not exist")
                || message.equals("The classroom with that roomNumber does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.equals("That classroom is taken by another teacher")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Teacher> found = teacherService.getTeacherById(id);
        if (found.isPresent()) {
            return new ResponseEntity<>(found.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The teacher with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Teacher teacher) {
        String message = teacherService.updateTeacher(id, teacher);
        if (message.equals("The teacher with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = teacherService.deleteTeacher(id);
        if (message.equals("The teacher with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}