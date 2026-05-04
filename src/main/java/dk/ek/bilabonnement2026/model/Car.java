package dk.ek.bilabonnement2026.model;

public class Car {
    private int carId;
    private int carModelId; // FK fra car_model
    private String vinNumber;
    private String licensePlate;
    private String colour;
    private String status;
    private double monthlyPrice;


    public Car(int carId, int carModelId, String vinNumber, String licensePlate,
               double monthlyPrice, String status, String colour){
        this.carId = carId;
        this.carModelId = carModelId;
        this.vinNumber = vinNumber;
        this.licensePlate = licensePlate;
        this.monthlyPrice = monthlyPrice;
        this.status = status;
        this.colour = colour;
    }
    public Car(int carModelId, String vinNumber, String licensePlate,
               double monthlyPrice, String status, String colour){
        this.carModelId = carModelId;
        this.vinNumber = vinNumber;
        this.licensePlate = licensePlate;
        this.monthlyPrice = monthlyPrice;
        this.status = status;
        this.colour = colour;
    }

    public int getCarId() {
        return carId;
    }

    public int getCarModelId() {
        return carModelId;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getColour() {
        return colour;
    }

    public String getStatus() {
        return status;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

}
