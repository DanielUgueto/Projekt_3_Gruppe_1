package dk.ek.bilabonnement2026.model;

public class CustomerAddress {

    private int addressId;
    private int customerId;
    private String zipCode;
    private String streetName;
    private String houseNumber;
    private String floor;

    public CustomerAddress() {
    }

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

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }
}