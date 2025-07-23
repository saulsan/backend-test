package com.neology.parking.model;

import jakarta.persistence.*;
import java.util.Calendar;

@Entity
public class Stay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vehicle vehicle;

    private Calendar entryTime;
    private Calendar exitTime;

    public Stay() {}

    public Stay(Vehicle vehicle, Calendar entryTime) {
        this.vehicle = vehicle;
        this.entryTime = entryTime;
    }

    public void setExitTime(Calendar exitTime) {
        this.exitTime = exitTime;
    }

    public Calendar getEntryTime() { return entryTime; }
    public Calendar getExitTime() { return exitTime; }
    public Vehicle getVehicle() { return vehicle; }
}
