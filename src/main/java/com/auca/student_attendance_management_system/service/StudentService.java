package com.auca.student_attendance_management_system.service;

import com.auca.student_attendance_management_system.model.Student;
import com.auca.student_attendance_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public String saveStudent(Student student) {
        studentRepository.save(student);
        return "Student saved successfully";
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(UUID id) {
        return studentRepository.findById(id);
    }

    public String updateStudent(UUID id, Student newData) {
        Optional<Student> found = studentRepository.findById(id);
        if (found.isEmpty()) {
            return "The student with that id does not exist";
        }
        Student student = found.get();
        student.setFirstName(newData.getFirstName());
        student.setLastName(newData.getLastName());
        student.setRegNumber(newData.getRegNumber());
        student.setClassName(newData.getClassName());
        student.setGuardianPhone(newData.getGuardianPhone());
        studentRepository.save(student);
        return "Student updated successfully";
    }

    public String deleteStudent(UUID id) {
        if (!studentRepository.existsById(id)) {
            return "The student with that id does not exist";
        }
        studentRepository.deleteById(id);
        return "Student deleted successfully";
    }

    public List<Student> getStudentsByLastName(String lastName) {
        return studentRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }
}