

enum FuelType {
    PETROL,
    DIESEL,
    ELECTRIC,
    HYBRID,
    CNG
}

abstract class Vehicle {

    private int id;
    private String brand;
    private String model;
    private int year;
    private FuelType fuelType;
    private boolean rented;
    private int counter = 5;

    public Vehicle() {
    }

    public Vehicle(String brand, String model, int year, FuelType fuelType) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.fuelType = fuelType;
        this.id = counter++;
        this.rented = false;

    }

    abstract double calculateRentalPrice(int days);

    public void getInfo() {
        System.out.println("\nID: " + this.id
                + "\tBrand: " + this.brand
                + "\tModel: " + this.model
                + "\tYear: " + this.year
                + "\nFuel-Type: " + this.fuelType
                + "\tRent Rate: " + this.calculateRentalPrice(1));
    }

    @Override
    public String toString() {
        return "\nID: " + this.id
                + "\tBrand: " + this.brand
                + "\tModel: " + this.model
                + "\tFuel-Type: " + this.fuelType
                + "\nPrice: " + this.calculateRentalPrice(1);
    }

    public String getBrand() {
        return brand;
    }

    public String getFuelType() {
        return fuelType.toString();
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public boolean isRented() {
        return rented;
    }

    public int getYear() {
        return year;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }
}
