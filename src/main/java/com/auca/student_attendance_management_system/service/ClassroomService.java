package com.auca.student_attendance_management_system.service;

import com.auca.student_attendance_management_system.model.Classroom;
import com.auca.student_attendance_management_system.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    public String saveClassroom(Classroom classroom) {
        classroomRepository.save(classroom);
        return "Classroom saved successfully";
    }

    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    public Optional<Classroom> getClassroomById(UUID id) {
        return classroomRepository.findById(id);
    }

    public String updateClassroom(UUID id, Classroom newData) {
        Optional<Classroom> found = classroomRepository.findById(id);
        if (found.isEmpty()) {
            return "The classroom with that id does not exist";
        }
        Classroom classroom = found.get();
        classroom.setRoomNumber(newData.getRoomNumber());
        classroom.setName(newData.getName());
        classroomRepository.save(classroom);
        return "Classroom updated successfully";
    }

    public String deleteClassroom(UUID id) {
        if (!classroomRepository.existsById(id)) {
            return "The classroom with that id does not exist";
        }
        classroomRepository.deleteById(id);
        return "Classroom deleted successfully";
    }

    public List<Classroom> getFreeClassrooms() {
        return classroomRepository.findFreeClassrooms();
    }
}