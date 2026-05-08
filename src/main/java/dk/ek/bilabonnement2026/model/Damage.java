package dk.ek.bilabonnement2026.model;

public class Damage {
    private int damageId;
    private int damageReportId;
    private int damageCategoryId;

    public Damage(int damageId, int damageReportId, int damageCategoryId) {
        this.damageId = damageId;
        this.damageReportId = damageReportId;
        this.damageCategoryId = damageCategoryId;
    }

    public Damage(int damageReportId, int damageCategoryId) {
        this.damageReportId = damageReportId;
        this.damageCategoryId = damageCategoryId;
    }

    public int getDamageId() {
        return damageId;
    }

    public int getDamageReportId() {
        return damageReportId;
    }

    public int getDamageCategoryId() {
        return damageCategoryId;
    }
}
