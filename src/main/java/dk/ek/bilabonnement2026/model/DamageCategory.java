package dk.ek.bilabonnement2026.model;

//Rune
public class DamageCategory {

    private int damageCategoryId;
    private String name;
    private double standardPrice;
    private String description;
    private boolean isActive;

    public DamageCategory(int damageCategoryId, String name, double standardPrice, String description, boolean isActive) {
        this.damageCategoryId = damageCategoryId;
        this.name = name;
        this.standardPrice = standardPrice;
        this.description = description;
        this.isActive = isActive;
    }

    public DamageCategory(String name, double standardPrice, String description){
        this.name = name;
        this.standardPrice = standardPrice;
        this.description = description;
    }


    public int getDamageCategoryId() {
        return damageCategoryId;
    }

    public String getName() {
        return name;
    }

    public double getStandardPrice() {
        return standardPrice;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsActive(){
        return isActive;
    }
}
