package dk.ek.bilabonnement2026.model;

import java.time.LocalDate;

public class RentalContract {
    private int rentalContractId;
    private int employeeId;
    private int customerId;
    private int carId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String pickupLocation;
    private String status;
    private String subscriptionType;

    public RentalContract(int rentalContractId, int employeeId,
                          int customerId, int carId, LocalDate startDate,
                          LocalDate endDate, String pickupLocation, String status, String subscriptionType) {
        this.rentalContractId = rentalContractId;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickupLocation = pickupLocation;
        this.status = status;
        this.subscriptionType = subscriptionType;
    }

    public RentalContract(int employeeId, int customerId, int carId, LocalDate startDate, LocalDate endDate, String pickupLocation, String status, String subscriptionType) {
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickupLocation = pickupLocation;
        this.status = status;
        this.subscriptionType = subscriptionType;
    }

    public RentalContract(int rentalContractId, LocalDate startDate, LocalDate endDate, String pickupLocation, String subscriptionType){
        this.rentalContractId = rentalContractId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickupLocation = pickupLocation;
        this.subscriptionType = subscriptionType;
    }

    public int getRentalContractId() {
        return rentalContractId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getCarId() {
        return carId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getStatus() {
        return status;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }
}
