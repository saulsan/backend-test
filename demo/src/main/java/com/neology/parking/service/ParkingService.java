package com.neology.parking.service;

import com.neology.parking.model.*;
import com.neology.parking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ParkingService {

    @Autowired private VehicleRepository vehicleRepo;
    @Autowired private StayRepository stayRepo;

    public void registerEntry(String plate) {
        Vehicle v = vehicleRepo.findById(plate)
                .orElse(new Vehicle(plate, VehicleType.NON_RESIDENT));
        vehicleRepo.save(v);

        Stay stay = new Stay();
        stay.setVehicle(v);
        stay.setEntryTime(LocalDateTime.now());
        stayRepo.save(stay);
    }

    public double registerExit(String plate) {
        Vehicle v = vehicleRepo.findById(plate)
                .orElseThrow(() -> new RuntimeException("Vehículo no registrado"));

        Stay stay = stayRepo.findByVehicleAndExitTimeIsNull(v).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No hay entrada registrada"));

        stay.setExitTime(LocalDateTime.now());
        stayRepo.save(stay);

        long minutes = stay.getDurationInMinutes();
        if (v.getType().isResident()) {
            v.setAccumulatedMinutes(v.getAccumulatedMinutes() + (int) minutes);
            vehicleRepo.save(v);
            return 0;
        } else if (v.getType().isPayingAtExit()) {
            return minutes * v.getType().getRatePerMinute();
        }
        return 0;
    }

    public void registerOfficial(String plate) {
        vehicleRepo.save(new Vehicle(plate, VehicleType.OFFICIAL));
    }

    public void registerResident(String plate) {
        vehicleRepo.save(new Vehicle(plate, VehicleType.RESIDENT));
    }

    public void resetMonth() {
        stayRepo.deleteAll(stayRepo.findByVehicle_Type(VehicleType.OFFICIAL));
        vehicleRepo.findAll().stream()
                .filter(v -> v.getType().isResident())
                .forEach(v -> {
                    v.setAccumulatedMinutes(0);
                    vehicleRepo.save(v);
                });
    }

    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Núm. placa\tTiempo estacionado (min.)\tCantidad a pagar\n");
        vehicleRepo.findAll().stream()
                .filter(v -> v.getType().isResident())
                .forEach(v -> sb.append(String.format("%s\t%d\t%.2f\n",
                        v.getPlate(), v.getAccumulatedMinutes(),
                        v.getAccumulatedMinutes() * v.getType().getRatePerMinute())));
        return sb.toString();
    }
}
