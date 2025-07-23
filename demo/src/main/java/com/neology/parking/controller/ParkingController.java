package com.neology.parking.controller;

import com.neology.parking.dto.PlateRequest;
import com.neology.parking.service.ParkingService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingService service;

    public ParkingController(ParkingService service) {
        this.service = service;
    }

    @PostMapping("/entry")
    public ResponseEntity<?> registerEntry(@RequestBody PlateRequest req) {
        service.registerEntry(req.getPlate());
        return ResponseEntity.ok("Entrada registrada");
    }

    @PostMapping("/exit")
    public ResponseEntity<?> registerExit(@RequestBody PlateRequest req) {
        service.registerExit(req.getPlate());
        return ResponseEntity.ok("Salida registrada");
    }

    @PostMapping("/official")
    public ResponseEntity<?> registerOfficial(@RequestBody PlateRequest req) {
        service.registerOfficial(req.getPlate());
        return ResponseEntity.ok("Vehículo oficial dado de alta");
    }

    @PostMapping("/resident")
    public ResponseEntity<?> registerResident(@RequestBody PlateRequest req) {
        service.registerResident(req.getPlate());
        return ResponseEntity.ok("Vehículo residente dado de alta");
    }

    @PostMapping("/start-month")
    public ResponseEntity<?> startNewMonth() {
        service.startNewMonth();
        return ResponseEntity.ok("Mes reiniciado");
    }

    @GetMapping("/report")
    public ResponseEntity<FileSystemResource> generateReport(@RequestParam String filename) {
        File file = service.generateResidentPaymentsReport(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("text/plain"))
                .body(new FileSystemResource(file));
    }
}