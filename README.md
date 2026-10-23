# Student Attendance Management System Backend

**Student:** Mucyo Celse
**Student ID:** 27692
**Project:** Student Attendance Management System
**Course:** Web Technology and Internet, AUCA (Mid-Term Project Examination)

## Description
A Spring Boot REST API that replaces paper roll-calls in a school. Students file leave requests that a class teacher reviews (REQUESTED, REVIEWED, GRANTED or DENIED). Once a leave request is granted, the teacher assigns catch-up tasks.

## Technologies
- Java 17
- Spring Boot (Spring Web, Spring Data JPA)
- PostgreSQL
- Maven

## How to run
1. Install JDK 17, Maven and PostgreSQL.
2. Create the database:
   ```sql
   CREATE DATABASE student_attendance;
   ```
3. Clone the repository:
   ```
   git clone https://github.com/celsemucyo80-blip/student-attendance-management-system-backend.git
   ```
4. In `src/main/resources/application.properties`, set `spring.datasource.password` to your PostgreSQL password.
5. Start the API:
   ```
   mvn spring-boot:run
   ```
   The API runs at `http://localhost:1014`.
6. Test the endpoints with the Postman collection `student-attendance-management-system-midterm.postman_collection.json` in the repository root.