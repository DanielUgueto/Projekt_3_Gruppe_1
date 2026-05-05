package dk.ek.bilabonnement2026.model;

public class CustomerAddress {

    private int addressId;
    private int customerId;
    private String zipCode;
    private String streetName;
    private String houseNumber;
    private String floor;

    public CustomerAddress(int addressId, int customerId, String zipCode, String streetName, String houseNumber, String floor) {
        this.addressId = addressId;
        this.customerId = customerId;
        this.zipCode = zipCode;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.floor = floor;
    }

    public int getAddressId() {
        return addressId;
    }

    public int getCustomerId() {
        return customerId;
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