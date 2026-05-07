package dk.ek.bilabonnement2026.model;

import java.time.LocalDate;

public class DamageReport {

private int damageReportId; //pk
private int rentalContractId; //fk
private int employeeId; //fk
private LocalDate createdAt;
private double totalPrice;
private String description;

    public DamageReport(int damageReportId, int rentalContractId, int employeeId, LocalDate createdAt, double totalPrice, String description) {
        this.damageReportId = damageReportId;
        this.rentalContractId = rentalContractId;
        this.employeeId = employeeId;
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
        this.description = description;
    }

    public DamageReport(int rentalContractId, int employeeId, LocalDate createdAt, double totalPrice, String description) {
        this.rentalContractId = rentalContractId;
        this.employeeId = employeeId;
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
        this.description = description;
    }

    public int getDamageReportId() {
        return damageReportId;
    }

    public int getRentalContractId() {
        return rentalContractId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getDescription() {
        return description;
    }
}
