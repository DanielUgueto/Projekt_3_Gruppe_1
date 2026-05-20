package dk.ek.bilabonnement2026.model;

public class Customer extends Person {
    private int customerId;
    private String driversLicenseNumber;
    private String cprNumber;
    private String phoneNumber;
    private CustomerAddress address;

    //Bruges til at hente Customer fra DB
    public Customer(int customerId, String firstName, String lastName, String driversLicenseNumber, String cprNumber, String email, String phoneNumber, Boolean isActive) {
        super(firstName, lastName,email,isActive);
        this.customerId = customerId;
        this.driversLicenseNumber = driversLicenseNumber;
        this.cprNumber = cprNumber;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String firstName, String lastName, String driversLicenseNumber, String cprNumber, String email, String phoneNumber, Boolean isActive) {
        super(firstName, lastName,email,isActive);
        this.driversLicenseNumber = driversLicenseNumber;
        this.cprNumber = cprNumber;
        this.phoneNumber = phoneNumber;
    }

    //Bruges til at hente en Customer fra DB, bruges når controller skal vise et overview.
    public Customer(int customerId, String firstName, String lastName, String driversLicenseNumber,
                    String cprNumber, String email, String phoneNumber, boolean isActive, CustomerAddress address) {
        super(firstName, lastName,email,isActive);
        this.customerId = customerId;
        this.driversLicenseNumber = driversLicenseNumber;
        this.cprNumber = cprNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getDriversLicenseNumber() {
        return driversLicenseNumber;
    }

    public String getCprNumber() {
        return cprNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CustomerAddress getAddress() {
        return address;
    }
}