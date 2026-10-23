package com.auca.student_attendance_management_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;


@Entity 
@Table(name = "classroom")
public class Classroom {

    @Id 
    @GeneratedValue 
    private UUID id;

    private int roomNumber;
    private String name;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name =name; }

}