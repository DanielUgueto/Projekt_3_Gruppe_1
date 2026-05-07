package dk.ek.bilabonnement2026.model;

public class ZipCode {
    private String zipCode;
    private String country;
    private String city;

    public ZipCode(String zipCode,String city, String country){
        this.zipCode = zipCode;
        this.country = country;
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }
}
