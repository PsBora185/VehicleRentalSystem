# Vehicle Rental System

A simple Java project to manage vehicle rentals, including cars and bikes, with rental, return, and search functionality. Vehicle data is stored in a JSON file.

---

## Features

- Add new vehicles (Car or Bike)
- View available vehicles
- Search vehicle by ID
- Rent and return vehicles
- Data saved in `vehicles.json`
- Custom exceptions for error handling

---

## How to Run

1. Make sure you have Java installed.
2. Add the `jackson-databind` library to your project.
3. Run `RentVehicleService.java`.
4. Follow the menu prompts to interact with the system.

---

## File Structure

src/
├─ Vehicle.java
├─ Car.java
├─ Bike.java
├─ RentVehicleService.java
├─ VehicleData.java
└─ resources/vehicles.json

---

## Notes

- IDs are generated automatically , in code it's for testing. You can Change Generation logic.
- Rental price is calculated based on vehicle type, age, and features.
- Exceptions used: `VehicleNotFoundException`, `VehicleAvailablityException`.
