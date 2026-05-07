package dk.ek.bilabonnement2026.model;

public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private int driversLicenseNumber;
    private String cprNumber;
    private String email;
    private int phoneNumber;

    public Customer(int customerId, String firstName, String lastName, int driversLicenseNumber, String cprNumber, String email, int phoneNumber) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.driversLicenseNumber = driversLicenseNumber;
        this.cprNumber = cprNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public Customer( String firstName, String lastName, String driversLicenseNumber, String cprNumber, String email, int phoneNumber) {
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

    public int getDriversLicenseNumber() {
        return driversLicenseNumber;
    }

    public String getCprNumber() {
        return cprNumber;
    }

    public String getEmail() {
        return email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
}