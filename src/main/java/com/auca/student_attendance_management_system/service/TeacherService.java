package com.auca.student_attendance_management_system.service;

import com.auca.student_attendance_management_system.model.Teacher;
import com.auca.student_attendance_management_system.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public String saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
        return "Teacher saved successfully";
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacherById(UUID id) {
        return teacherRepository.findById(id);
    }

    public String updateTeacher(UUID id, Teacher newData) {
        Optional<Teacher> found = teacherRepository.findById(id);
        if (found.isEmpty()) {
            return "The teacher with that id does not exist";
        }
        Teacher teacher = found.get();
        teacher.setFirstName(newData.getFirstName());
        teacher.setLastName(newData.getLastName());
        teacher.setEmail(newData.getEmail());
        if (newData.getClassroom() != null) {
            teacher.setClassroom(newData.getClassroom());
        }
        teacherRepository.save(teacher);
        return "Teacher updated successfully";
    }

    public String deleteTeacher(UUID id) {
        if (!teacherRepository.existsById(id)) {
            return "The teacher with that id does not exist";
        }
        teacherRepository.deleteById(id);
        return "Teacher deleted successfully";
    }
}