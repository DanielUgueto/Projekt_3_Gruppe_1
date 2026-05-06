package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.Car;
import dk.ek.bilabonnement2026.model.CarOverview;
import dk.ek.bilabonnement2026.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    public void createCar(Car car){
        Car validatorCar = carRepository.findCarByVinNumber(car.getVinNumber());
        if(validatorCar != null){
            throw new IllegalArgumentException("En bil med dette stelnummer eksistere allerede");
        }

        // Validate if vinNumber exsists
        validatorCar = carRepository.findCarByCarNumber(car.getCarId());
        if(validatorCar != null){
            throw new IllegalArgumentException("En bil med dette vognnummer eksistere allerede");
        }

        carRepository.saveCar(car);
    }

    public ArrayList<CarOverview> findCarsWithDetails() {
        return carRepository.findAllCarsWithDetails();
    }

    public ArrayList<CarOverview> findCarsWithDetailsByStatus(String status){
        return carRepository.findAllCarsWithDetailsByStatus(status);
    }
}
