package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.CarModel;
import dk.ek.bilabonnement2026.repository.CarModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarModelService {

    @Autowired
    CarModelRepository carModelRepository;

    public List<CarModel> getAllCarModels(){
        return carModelRepository.getAllCarModels();
    }

    public CarModel getCarModelByCarModelId(int carModelId){
        return carModelRepository.getCarModelByCarModelId(carModelId);
    }

    public void updateCarModel(CarModel carModel) {
        carModelRepository.updateCarModel(carModel);
    }
}
