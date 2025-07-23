package com.neology.parking.repository;

import com.neology.parking.model.Stay;
import com.neology.parking.model.Vehicle;
import com.neology.parking.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StayRepository extends JpaRepository<Stay, Long> {
    List<Stay> findByVehicleAndExitTimeIsNull(Vehicle vehicle);
    List<Stay> findByVehicle(Vehicle vehicle);
    List<Stay> findByVehicle_Type(VehicleType type);
}
