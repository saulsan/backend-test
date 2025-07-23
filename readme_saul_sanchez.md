# 🚗 Parking System Backend

Sistema de control de estacionamiento con Spring Boot, basado en tipos de vehículos (`OFFICIAL`, `RESIDENT`, `NON_RESIDENT`) usando `enum` para reglas de negocio y persistencia con H2 en memoria.

---

## 📦 Stack Tecnológico

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 Database (en memoria)
- Maven
- JUnit 5

---

## 🚀 Ejecución Local

```bash
# Clonar repositorio
$ git clone <repo-url>
$ cd backend-test

# Compilar y correr
$ mvn spring-boot:run
```

Accede a:

- H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  - JDBC URL: `jdbc:h2:mem:testdb`
  - User: `sa`
  - Password: *(vacío)*

---

## 📁 Estructura Principal

```
com.neology.parking
├── controller
│   └── ParkingController.java
├── model
│   ├── Vehicle.java
│   ├── VehicleType.java (enum)
│   └── Stay.java
├── repository
│   ├── VehicleRepository.java
│   └── StayRepository.java
├── service
│   └── ParkingService.java
├── config
│   └── H2Config.java
└── ParkingApplication.java
```

---

## 🎯 Endpoints Principales

Todos aceptan `Content-Type: application/json`:

| Método | URL                          | Cuerpo / Parámetro | Descripción                         |
| ------ | ---------------------------- | ------------------ | ----------------------------------- |
| POST   | `/entry/{plate}`             |                    | Registrar entrada de vehículo       |
| POST   | `/exit/{plate}`              |                    | Registrar salida y cálculo de pago  |
| POST   | `/register/resident/{plate}` |                    | Registrar vehículo residente        |
| POST   | `/register/official/{plate}` |                    | Registrar vehículo oficial          |
| POST   | `/reset`                     |                    | Reiniciar mes/reseteo de residentes |
| GET    | `/report`                    |                    | Generar reporte mensual             |

---

## 🧪 Pruebas Unitarias

Ejecutar pruebas con:

```bash
mvn test
```

Archivo: `ParkingServiceTest.java`

Incluye:

- Entrada/salida para oficiales
- Acumulación mensual para residentes
- Cálculo de cobro para no residentes

---

## 🗃️ Datos Iniciales (`data.sql`)

```sql
INSERT INTO VEHICLE (plate, type, accumulated_minutes) VALUES ('RES123', 'RESIDENT', 120);
INSERT INTO VEHICLE (plate, type, accumulated_minutes) VALUES ('OFF123', 'OFFICIAL', 0);
INSERT INTO VEHICLE (plate, type, accumulated_minutes) VALUES ('NON456', 'NON_RESIDENT', 0);```

---

## ✅ Estado

✅ Funcional: entradas, salidas, cálculo dinámico de cobros, persistencia, reporte y pruebas.

📌 Siguiente paso opcional: generar interfaz frontend (React o Thymeleaf).

---

¿Preguntas? Contáctanos saulspowa@gmail.com✉️

