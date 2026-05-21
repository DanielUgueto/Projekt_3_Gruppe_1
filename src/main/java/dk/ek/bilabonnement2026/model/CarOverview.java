package dk.ek.bilabonnement2026.model;

import java.time.LocalDate;

// Nico
public class CarOverview {
    private int carId;
    private String brandName;
    private String modelName;
    private String equipmentLevel;
    private String shiftGearType;
    private String vinNumber;
    private String licensePlate;
    private double monthlyPrice;
    private String status;
    private String colour;
    private LocalDate registrationDate;
    private String fuelType;
    private int rentalContractId;

    public CarOverview(int carId, String brandName, String modelName, String equipmentLevel,
                       String shiftGearType, String vinNumber, String licensePlate, double monthlyPrice, String status,
                       String colour, LocalDate registrationDate, String fuelType, int rentalContractId) {
        this.carId = carId;
        this.brandName = brandName;
        this.modelName = modelName;
        this.equipmentLevel = equipmentLevel;
        this.shiftGearType = shiftGearType;
        this.vinNumber = vinNumber;
        this.licensePlate = licensePlate;
        this.monthlyPrice = monthlyPrice;
        this.status = status;
        this.colour = colour;
        this.registrationDate = registrationDate;
        this.fuelType = fuelType;
        this.rentalContractId = rentalContractId;
    }

    public CarOverview(String brandName, int carId, String colour, String equipmentLevel,
                       String licensePlate, String modelName, double monthlyPrice,
                       String shiftGearType, String status, String vinNumber, LocalDate registrationDate, String fuelType) {
        this.brandName = brandName;
        this.carId = carId;
        this.colour = colour;
        this.equipmentLevel = equipmentLevel;
        this.licensePlate = licensePlate;
        this.modelName = modelName;
        this.monthlyPrice = monthlyPrice;
        this.shiftGearType = shiftGearType;
        this.status = status;
        this.vinNumber = vinNumber;
        this.registrationDate = registrationDate;
        this.fuelType = fuelType;
    }

    public String getBrandName() {
        return brandName;
    }

    public int getCarId() {
        return carId;
    }

    public String getColour() {
        return colour;
    }

    public String getEquipmentLevel() {
        return equipmentLevel;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getModelName() {
        return modelName;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    public String getShiftGearType() {
        return shiftGearType;
    }

    public String getStatus() {
        return status;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public String getFuelType() {
        return fuelType;
    }

    public int getRentalContractId() {
        return rentalContractId;
    }
}
