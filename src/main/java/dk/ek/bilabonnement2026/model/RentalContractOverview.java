package dk.ek.bilabonnement2026.model;

import java.time.LocalDate;

//Rune
public class RentalContractOverview {
    private int rentalContractId;
    private String customerFirstName;
    private String customerLastName;
    private String brandName;
    private String modelName;
    private String licensePlate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String pickupLocation;
    private double monthlyPrice;


    public RentalContractOverview(int rentalContractId, String customerFirstName, String customerLastName,
                                  String brandName, String modelName, String licensePlate, LocalDate startDate,
                                  LocalDate endDate, String status, String pickupLocation, double monthlyPrice) {
        this.rentalContractId = rentalContractId;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.brandName = brandName;
        this.modelName = modelName;
        this.licensePlate = licensePlate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.pickupLocation = pickupLocation;
        this.monthlyPrice = monthlyPrice;
    }

    public int getRentalContractId() {
        return rentalContractId;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }
}
