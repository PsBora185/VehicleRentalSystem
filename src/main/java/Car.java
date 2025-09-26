public class Car extends Vehicle {

    private int seats;
    private boolean ac;

    public Car() {
    }

    public Car(String brand, String model, int year, FuelType fuelType, int seats, boolean ac) {
        super(brand, model, year, fuelType);
        this.seats = seats;
        this.ac = ac;
    }

    @Override
    double calculateRentalPrice(int days) {
        double baseRate = 100;
        double totalPrice = baseRate * days;

        totalPrice += this.ac ? 15 * days : 0;

        totalPrice += this.seats > 4 ? 10 * days : 0;

        totalPrice *= 2025 - this.getYear() > 5 ? 0.8 : 1; // 20% discount on old vehicle
        return totalPrice;
    }

    public void getInfo() {
        super.getInfo();
        System.out.println("Seats: " + this.seats
                + "\t" + (ac ? "AC available" : "AC not available"));
    }

    public boolean isAc() {
        return ac;
    }


    public int getSeats() {
        return seats;
    }
}
