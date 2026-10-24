package com.auca.student_attendance_management_system.service;

import com.auca.student_attendance_management_system.model.Subject;
import com.auca.student_attendance_management_system.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public String saveSubject(Subject subject) {
        subjectRepository.save(subject);
        return "Subject saved successfully";
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Optional<Subject> getSubjectById(UUID id) {
        return subjectRepository.findById(id);
    }

    public String updateSubject(UUID id, Subject newData) {
        Optional<Subject> found = subjectRepository.findById(id);
        if (found.isEmpty()) {
            return "The subject with that id does not exist";
        }
        Subject subject = found.get();
        subject.setName(newData.getName());
        subjectRepository.save(subject);
        return "Subject updated successfully";
    }

    public String deleteSubject(UUID id) {
        if (!subjectRepository.existsById(id)) {
            return "The subject with that id does not exist";
        }
        subjectRepository.deleteById(id);
        return "Subject deleted successfully";
    }
}