package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.CarBrand;
import dk.ek.bilabonnement2026.repository.CarBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Nico
@Service
public class CarBrandService {

    @Autowired
    CarBrandRepository carBrandRepository;

    // Nico
    public List<CarBrand> getAllCarBrands() {
        return carBrandRepository.getAllBrands();
    }

    // Nico
    public void saveBrand(String brandName) {
        carBrandRepository.saveBrand(brandName);
    }

    // Nico
    public CarBrand getCarBrandByBrandName(String brandName) {
        return carBrandRepository.getCarBrandByBrandName(brandName);
    }
}
