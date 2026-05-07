package dk.ek.bilabonnement2026.model;

public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String driversLicenseNumber;
    private String cprNumber;
    private String email;
    private String phoneNumber;

    public Customer(int customerId, String firstName, String lastName, String driversLicenseNumber, String cprNumber, String email, String phoneNumber) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.driversLicenseNumber = driversLicenseNumber;
        this.cprNumber = cprNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


    public int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDriversLicenseNumber() {
        return driversLicenseNumber;
    }

    public String getCprNumber() {
        return cprNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}