package dk.ek.bilabonnement2026.model;

//Rune
public class CarModel {
    private int carModelId;
    private int carBrandId;
    private String modelName;
    private String equipmentLevel;
    private String shiftGearType;
    private String fuelType;

    public CarModel(int carModelId, int carBrandId, String modelName,
                    String equipmentLevel, String shiftGearType, String fuelType) {
        this.carModelId = carModelId;
        this.carBrandId = carBrandId;
        this.modelName = modelName;
        this.equipmentLevel = equipmentLevel;
        this.shiftGearType = shiftGearType;
        this.fuelType = fuelType;
    }

    public CarModel(int carBrandId, String modelName, String equipmentLevel,
                    String shiftGearType, String fuelType) {
        this.carBrandId = carBrandId;
        this.modelName = modelName;
        this.equipmentLevel = equipmentLevel;
        this.shiftGearType = shiftGearType;
        this.fuelType = fuelType;
    }

    public int getCarModelId() {
        return carModelId;
    }

    public int getCarBrandId() {
        return carBrandId;
    }

    public String getModelName() {
        return modelName;
    }

    public String getEquipmentLevel() {
        return equipmentLevel;
    }

    public String getShiftGearType() {
        return shiftGearType;
    }

    public String getFuelType() {
        return fuelType;
    }
}
