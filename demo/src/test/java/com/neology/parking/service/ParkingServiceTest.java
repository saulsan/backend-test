package com.neology.parking.service;

import com.neology.parking.model.Vehicle;
import com.neology.parking.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ParkingServiceTest {

    @Autowired
    ParkingService parkingService;

    @Autowired
    VehicleRepository vehicleRepo;

    @Test
    void testOfficialVehicleEntryExit() {
        parkingService.registerOfficial("OFF123");
        parkingService.registerEntry("OFF123");
        double amount = parkingService.registerExit("OFF123");
        assertEquals(0, amount, "Vehículo oficial no debe pagar");
    }

    @Test
    void testResidentAccumulation() throws InterruptedException {
        parkingService.registerResident("RES123");
        parkingService.registerEntry("RES123");
        sleep(1200); // Espera 1.2 segundos para asegurar >1 minuto
        parkingService.registerExit("RES123");

        Vehicle v = vehicleRepo.findById("RES123").orElseThrow();
        assertTrue(v.getAccumulatedMinutes() > 0, "Debe haber minutos acumulados");
    }

    @Test
    void testNonResidentCharge() throws InterruptedException {
        String plate = "NON123";
        parkingService.registerEntry(plate);
        sleep(1200);
        double charge = parkingService.registerExit(plate);
        assertTrue(charge > 0, "El no residente debe pagar");
    }

    @Test
    void testMonthlyReset() {
        parkingService.registerResident("RESET123");
        Vehicle v = vehicleRepo.findById("RESET123").orElseThrow();
        v.setAccumulatedMinutes(100);
        vehicleRepo.save(v);

        parkingService.resetMonth();

        Vehicle updated = vehicleRepo.findById("RESET123").orElseThrow();
        assertEquals(0, updated.getAccumulatedMinutes(), "El acumulado debe reiniciarse a 0");
    }

    @Test
    void testReportGeneration() {
        parkingService.registerResident("REP456");
        Vehicle v = vehicleRepo.findById("REP456").orElseThrow();
        v.setAccumulatedMinutes(300);
        vehicleRepo.save(v);

        String report = parkingService.generateReport();
        assertTrue(report.contains("REP456"), "El reporte debe contener la placa");
        assertTrue(report.contains("300"), "El reporte debe mostrar los minutos acumulados");
    }
}
