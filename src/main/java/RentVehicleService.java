import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class VehicleData {
    List<Car> cars;
    List<Bike> bikes;

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Bike> getBikes() {
        return bikes;
    }

    public void setBikes(List<Bike> bikes) {
        this.bikes = bikes;
    }
}

class VehicleNotFoundException extends Exception{
    public VehicleNotFoundException(String message){
        super(message);
    }
}

class VehicleAvailablityException extends Exception{
    public VehicleAvailablityException(String message){
        super(message);
    }
}

public class RentVehicleService {

    private static String path;
    private static final ObjectMapper mapper = new ObjectMapper();
    List<Vehicle> vehicles;
    static Scanner sc = new Scanner(System.in);

    public RentVehicleService() {
        path = "src/main/resources/vehicles.json";
        vehicles = new ArrayList<>();
        loadVehicles();
        System.out.println("\n---Welcome---\n");
    }

    public void loadVehicles() {
        try {
            VehicleData data = mapper.readValue(new File(path), VehicleData.class);

            if (data.getCars() != null) {
                vehicles.addAll(data.getCars());
            }
            if (data.getBikes() != null) {
                vehicles.addAll(data.getBikes());
            }
        } catch (IOException e) {
            System.out.println("Can't Load Vehicle !");
            e.printStackTrace();
        }
    }

    public void saveVehicles() {
        try {
            VehicleData data = new VehicleData();

            List<Car> allCars = new ArrayList<>();
            List<Bike> allBikes = new ArrayList<>();
            for (Vehicle vehicle : vehicles) {
                if (vehicle instanceof Car)
                    allCars.add((Car) vehicle);
                else if (vehicle instanceof Bike)
                    allBikes.add((Bike) vehicle);
            }

            data.setCars(allCars);
            data.setBikes(allBikes);
            mapper.writeValue(new File(path), data);
            System.out.println("Vehicle is Added.");
        } catch (Exception e) {
            System.out.println("Error in saving Vehicles !");
        }
    }

    public void start() {

        int choice = 0;

        while (true) {
            System.out.println("""
                    1. Add Vehicles\s
                    2. Show Available Vehicles\s
                    3. Find Vehicle By ID\s
                    4. Rent a Vehicle\s
                    5. Return Vehicle\s
                    6. Exit""");
            System.out.print("\nEnter your choice : ");
            choice = validIntInput();

            switch (choice) {
                case 1:
                    addVehicle();
                    break;
                case 2:
                    showAvailableVehicles();
                    break;
                case 3:
                    try {
                        findVehicleById(getVehicleId()).getInfo();
                    } catch (VehicleNotFoundException e) {
                        System.out.println("Exception Caught : "+e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        rentVehicle(getVehicleId());
                    } catch (VehicleNotFoundException | VehicleAvailablityException e) {
                        System.out.println("Exception Caught : "+e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        returnVehicle(getVehicleId());
                    } catch (VehicleNotFoundException | VehicleAvailablityException e) {
                        System.out.println("Exception Caught : "+e.getMessage());
                    }
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid Choice !");
            }
            System.out.println("\n");
        }
    }

    public int getVehicleId() {
        System.out.print("Enter Id : ");
        return validIntInput();
    }

    public int validIntInput() {
        while (!sc.hasNextInt()) {
            System.out.print("Invalid Input, Enter a Number : ");
            sc.next();
        }
        return sc.nextInt();
    }

    public void addVehicle() {
        System.out.print("Wanna Add Car or Bike : ");
        String vehicle = sc.next();

        if (vehicle.equalsIgnoreCase("car") || vehicle.equalsIgnoreCase("bike")) {

            System.out.println("Enter ");

            System.out.print("\nBrand : ");
            String brand = sc.next();

            System.out.print("\nModel : ");
            String model = sc.next();

            System.out.print("\nYear : ");
            int year = validIntInput();

            System.out.print("\nFuel Type (PETROL, DIESEL, ELECTRIC, HYBRID, CNG) : ");
            String fuel = sc.next().toUpperCase();

            FuelType fuelType = null;
            try {
                fuelType = FuelType.valueOf(fuel);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid fuel type ! Defaulting to PETROL.");
                fuelType = FuelType.PETROL;
            }

            if (vehicle.equals("car")) {
                System.out.print("\nNumber of Doors : ");
                int doors = validIntInput();

                System.out.print("\nNumber of Seats : ");
                int seats = validIntInput();

                System.out.print("\nHave AC (yes/no) : ");
                String ans = sc.next();
                boolean ac = ans.equalsIgnoreCase("yes");

                vehicles.add(new Car(brand, model, year, fuelType, seats, ac));
            } else {
                System.out.print("\nType of Bike : ");
                String type = sc.next();

                System.out.print("\nType of Engine : ");
                String engine = sc.next();

                vehicles.add(new Bike(brand, model, year, fuelType, type));
            }
            saveVehicles();
        } else {
            System.out.println("Invalid vehicle !");
        }
    }

    public void showAvailableVehicles() {
        vehicles.stream().filter(vehicle -> !vehicle.isRented()).forEach(System.out::println);
    }

    public Vehicle findVehicleById(int id) throws VehicleNotFoundException {

        Vehicle vehicle = vehicles.stream()
                .filter(v -> v.getId() == id)
                .findFirst().orElse(null);

        if (vehicle == null) {
            throw new VehicleNotFoundException("Can't Find Vehicle With That ID");
        }

        return vehicle;
    }

    public void rentVehicle(int id) throws VehicleNotFoundException, VehicleAvailablityException {
        Vehicle vehicle = findVehicleById(id);

        if (vehicle.isRented())
            throw new VehicleAvailablityException("Vehicle IS Already Rented !");

        vehicle.setRented(true);
        System.out.println("Vehicle with ID "+id+" has Rented Successfully.");

    }

    public void returnVehicle(int id) throws VehicleNotFoundException, VehicleAvailablityException {
        Vehicle vehicle = findVehicleById(id);

        if (!vehicle.isRented())
            throw new VehicleAvailablityException("Vehicle IS Not Rented !");

        vehicle.setRented(false);
        System.out.println("Vehicle with ID "+id+" has Returned Successfully.");

    }

    public static void main(String[] args) {
        new RentVehicleService().start();
    }
}
