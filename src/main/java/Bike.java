
public class Bike extends Vehicle {

    private String type;

    public Bike() {
    }

    public Bike(String brand, String model, int year, FuelType fuelType, String type) {
        super(brand, model, year, fuelType);
        this.type = type;
    }

    public void getInfo() {
        super.getInfo();
        System.out.print("Type: " + this.type);
    }

    @Override
    double calculateRentalPrice(int days) {
        double baseRate = 100;
        double totalPrice = baseRate * days;

        totalPrice += this.type.equalsIgnoreCase("Sport") ? 10 * days : 0;

        totalPrice *= 2025 - this.getYear() > 5 ? 0.8 : 1; // 20% discount on old vehicle
        return totalPrice;
    }

    public String getType() {
        return type;
    }

}
