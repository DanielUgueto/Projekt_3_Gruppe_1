package dk.ek.bilabonnement2026.model;

public class DamageCategory {

    private int damageCategoryId;

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

    private String name;
    private double standardPrice;
    private String description;

    public DamageCategory(int damageCategoryId, String name, double standardPrice, String description) {
        this.damageCategoryId = damageCategoryId;
        this.name = name;
        this.standardPrice = standardPrice;
        this.description = description;
    }
}
