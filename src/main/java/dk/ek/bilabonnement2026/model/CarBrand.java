package dk.ek.bilabonnement2026.model;

//Rune
public class CarBrand {
    private int carBrandId;
    private String brandName;

    public CarBrand(int carBrandId, String brandName){
        this.carBrandId = carBrandId;
        this.brandName = brandName;
    }
    public CarBrand(String brandName){
        this.brandName = brandName;
    }

    public int getCarBrandId(){
        return carBrandId;
    }
    public String getBrandName(){
        return brandName;
    }
}
