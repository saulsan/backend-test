
package com.neology.parking.model;

import jakarta.persistence.*;
import java.util.Calendar;

@Entity
public class Vehicle {

    @Id
    private String plate;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    private int accumulatedMinutes = 0;

    private Calendar registeredAt;

    public Vehicle() {
        this.registeredAt = Calendar.getInstance();
    }

    public Vehicle(String plate, VehicleType type) {
        this.plate = plate;
        this.type = type;
        this.registeredAt = Calendar.getInstance();
    }

    public String getPlate() { return plate; }
    public VehicleType getType() { return type; }
    public int getAccumulatedMinutes() { return accumulatedMinutes; }
    public Calendar getRegisteredAt() { return registeredAt; }

    public void addMinutes(int minutes) {
        if (type == VehicleType.RESIDENT) {
            this.accumulatedMinutes += minutes;
        }
    }

    public void resetMinutes() {
        this.accumulatedMinutes = 0;
    }
}
