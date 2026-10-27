package com.auca.student_attendance_management_system.service;

import com.auca.student_attendance_management_system.model.Classroom;
import com.auca.student_attendance_management_system.model.Subject;
import com.auca.student_attendance_management_system.model.Teacher;
import com.auca.student_attendance_management_system.repository.ClassroomRepository;
import com.auca.student_attendance_management_system.repository.SubjectRepository;
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

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

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

    public List<Teacher> getTeachersBySubject(String name) {
        return teacherRepository.findTeachersBySubjectName(name);
    }

    public String addSubjectToTeacher(UUID teacherId, UUID subjectId) {
        Optional<Teacher> foundTeacher = teacherRepository.findById(teacherId);
        if (foundTeacher.isEmpty()) {
            return "The teacher with that id does not exist";
        }
        Optional<Subject> foundSubject = subjectRepository.findById(subjectId);
        if (foundSubject.isEmpty()) {
            return "That subject does not exist";
        }
        Teacher teacher = foundTeacher.get();
        Subject subject = foundSubject.get();

        for (Subject existing : teacher.getSubjects()) {
            if (existing.getId().equals(subject.getId())) {
                return "The teacher already has that subject";
            }
        }
        teacher.getSubjects().add(subject);
        teacherRepository.save(teacher);
        return "Subject added to the teacher";
    }

    public String changeClassroom(UUID teacherId, int roomNumber) {
        Optional<Teacher> foundTeacher = teacherRepository.findById(teacherId);
        if (foundTeacher.isEmpty()) {
            return "The teacher with that id does not exist";
        }
        Optional<Classroom> foundClassroom = classroomRepository.findByRoomNumber(roomNumber);
        if (foundClassroom.isEmpty()) {
            return "The classroom with that roomNumber does not exist";
        }
        Teacher teacher = foundTeacher.get();
        Classroom classroom = foundClassroom.get();

        if (teacher.getClassroom() != null && teacher.getClassroom().getId().equals(classroom.getId())) {
            return "Classroom changed successfully";
        }
        if (teacherRepository.existsByClassroom(classroom)) {
            return "That classroom is taken by another teacher";
        }
        teacher.setClassroom(classroom);
        teacherRepository.save(teacher);
        return "Classroom changed successfully";
    }
}