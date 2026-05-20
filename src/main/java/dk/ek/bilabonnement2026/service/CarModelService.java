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

    //Rune
    public void saveCarModel(CarModel carModel){
        carModelRepository.saveCarModel(carModel);
    }

    //Rune
    public CarModel getCarModelByModelName(String modelName){
        return carModelRepository.getCarModelByModelName(modelName);
    }

    //Rune
    public List<CarModel> getAllCarModels(){
        return carModelRepository.getAllCarModels();
    }

    // Nico
    public CarModel getCarModelByCarModelId(int carModelId){
        return carModelRepository.getCarModelByCarModelId(carModelId);
    }

    // Nico
    public void updateCarModel(CarModel carModel) {
        carModelRepository.updateCarModel(carModel);
    }
}
