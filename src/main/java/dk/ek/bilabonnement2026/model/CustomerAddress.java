package dk.ek.bilabonnement2026.model;

//Daniel
public class CustomerAddress {

    private int customerAddressId;
    private int customerId;
    private String zipCode;
    private String streetName;
    private String houseNumber;
    private String floor;

    public CustomerAddress(int customerAddressId, int customerId, String zipCode, String streetName, String houseNumber, String floor) {
        this.customerAddressId = customerAddressId;
        this.customerId = customerId;
        this.zipCode = zipCode;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.floor = floor;
    }

    public CustomerAddress(int customerId, String zipCode, String streetName, String houseNumber, String floor) {
        this.customerId = customerId;
        this.zipCode = zipCode;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.floor = floor;
    }

    public int getCustomerAddressId() {
        return customerAddressId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int newCustomerId) {
        customerId = newCustomerId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getFloor() {
        return floor;
    }
}