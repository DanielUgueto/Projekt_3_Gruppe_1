package dk.ek.bilabonnement2026.model;

public class CarModel {
    private int carModelId;
    private int carBrandId;
    private String modelName;
    private String equipmentLevel;
    private String shiftGearType;

    public CarModel(int carModelId, int carBrandId, String modelName,
                    String equipmentLevel, String shiftGearType) {
        this.carModelId = carModelId;
        this.carBrandId = carBrandId;
        this.modelName = modelName;
        this.equipmentLevel = equipmentLevel;
        this.shiftGearType = shiftGearType;
    }
    public CarModel(int carBrandId, String modelName,
                    String equipmentLevel, String shiftGearType) {
        this.carBrandId = carBrandId;
        this.modelName = modelName;
        this.equipmentLevel = equipmentLevel;
        this.shiftGearType = shiftGearType;
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
}
