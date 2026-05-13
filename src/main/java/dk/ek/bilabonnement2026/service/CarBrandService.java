package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.CarBrand;
import dk.ek.bilabonnement2026.repository.CarBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarBrandService {

    @Autowired
    CarBrandRepository carBrandRepository;

    public List<CarBrand> getAllCarBrands(){
        return carBrandRepository.getAllBrands();
    }
}
