package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.Car;
import dk.ek.bilabonnement2026.model.CarOverview;
import dk.ek.bilabonnement2026.repository.CarRepository;
import dk.ek.bilabonnement2026.repository.RentalContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    RentalContractRepository rentalContractRepository;

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

    public List<CarOverview> findCarsWithDetails() {
        return carRepository.findAllCarsWithDetails();
    }

    public List<CarOverview> findCarsWithDetailsByStatus(String status){
        return carRepository.findAllCarsWithDetailsByStatus(status);
    }

    public List<Car> findCarsByStatus(String status){
        return carRepository.findCarsByStatus(status);
    }

    public void changeCarStatusToExpired(int carId){
        Car car = carRepository.findCarByCarNumber(carId);

        if (rentalContractRepository.findRentalContractByCarId(carId) != null){ // make sure no active rental agreement is in place
            return;
        }

        if (!car.getStatus().equalsIgnoreCase("Ledig")) { // check car status to make sure it's not already rented
            return;
        }

        carRepository.updateCarStatus(carId, "Udgået");
    }
}
