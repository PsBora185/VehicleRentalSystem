import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TestRentVehicleService {

    private RentVehicleService rentVehicle;

    @BeforeEach
    void setup() {
        // Initialize RentVehicle (it will automatically call loadVehicles())
        rentVehicle = new RentVehicleService();
    }

    @Test
    void testLoadVehiclesNotEmpty() {
        List<Vehicle> list = rentVehicle.vehicles;

        // Check that vehicles list is not empty
        assertNotNull(list, "Vehicles list should not be null");
        assertFalse(list.isEmpty(), "Vehicles list should contain at least one vehicle");
    }

    @Test
    void testLoadSpecificVehicle() {
        List<Vehicle> list = rentVehicle.vehicles;

        // Look for a vehicle with known ID (change ID based on your JSON)
        int expectedId = 1;
        Vehicle foundVehicle = list.stream()
                .filter(v -> v.getId() == expectedId)
                .findFirst()
                .orElse(null);

        assertNotNull(foundVehicle, "Vehicle with ID " + expectedId + " should exist");

        // Optional: check properties
        assertEquals("Toyota", foundVehicle.getBrand(), "Brand should match");
    }

    @Test
    void testLoadVehicleTypes() {
        List<Vehicle> list = rentVehicle.vehicles;

        boolean hasCar = list.stream().anyMatch(v -> v instanceof Car);
        boolean hasBike = list.stream().anyMatch(v -> v instanceof Bike);

        assertTrue(hasCar, "There should be at least one Car");
        assertTrue(hasBike, "There should be at least one Bike");
    }

    @Test
    void testLoadVehicles() {
        // Call loadVehicles explicitly (optional since constructor already calls it)
        rentVehicle.loadVehicles();

        List<Vehicle> list = rentVehicle.vehicles;

        // Check that vehicles list is not null and has at least one item
        assertNotNull(list, "Vehicles list should not be null");
        assertTrue(list.size() > 0, "Vehicles list should contain at least one vehicle");
    }
}

