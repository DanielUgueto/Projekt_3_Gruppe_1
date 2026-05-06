package dk.ek.bilabonnement2026.model;

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
    private String registrationDate;
    private String fuelType;

    public CarOverview(String brandName, int carId, String colour, String equipmentLevel,
                       String licensePlate, String modelName, double monthlyPrice,
                       String shiftGearType, String status, String vinNumber, String registrationDate, String fuelType) {
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

    public String getRegistrationDate(){
        return registrationDate;
    }

    public String getFuelType(){
        return fuelType;
    }
}
