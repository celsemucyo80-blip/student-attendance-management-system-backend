package com.auca.student_attendance_management_system.service;

import com.auca.student_attendance_management_system.model.LeaveStatus;
import com.auca.student_attendance_management_system.repository.CatchUpTaskRepository;
import com.auca.student_attendance_management_system.repository.ClassroomRepository;
import com.auca.student_attendance_management_system.repository.LeaveRequestRepository;
import com.auca.student_attendance_management_system.repository.StudentRepository;
import com.auca.student_attendance_management_system.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private CatchUpTaskRepository catchUpTaskRepository;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("students", studentRepository.count());
        summary.put("teachers", teacherRepository.count());
        summary.put("freeClassrooms", (long) classroomRepository.findFreeClassrooms().size());
        summary.put("leaveRequestsToday", leaveRequestRepository.countByLeaveDate(LocalDate.now()));
        summary.put("requested", leaveRequestRepository.countByStatus(LeaveStatus.REQUESTED));
        summary.put("catchUpTasks", catchUpTaskRepository.count());
        return summary;
    }
}