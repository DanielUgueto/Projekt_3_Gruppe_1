package dk.ek.bilabonnement2026.model;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String driversLicenseNumber;
    private String cprNumber;
    private String email;
    private int phoneNumber;

    public Customer(int id, String firstName, String lastName, String driversLicenseNumber, String cprNumber, String email, int phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.driversLicenseNumber = driversLicenseNumber;
        this.cprNumber = cprNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String firstName, String lastName, String driversLicenseNumber, String cprNumber, String email, int phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.driversLicenseNumber = driversLicenseNumber;
        this.cprNumber = cprNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
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

    public int getPhoneNumber() {
        return phoneNumber;
    }
}