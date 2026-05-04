package dk.ek.bilabonnement2026.model;

public class Car {
    private int carId;
    private int carModelId; // FK fra car_model
    private String vinNumber;
    private String licensePlate;
    private String colour;
    private String status;
    private double monthlyPrice;

    public Car(){}

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

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(int carModelId) {
        this.carModelId = carModelId;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }
}
