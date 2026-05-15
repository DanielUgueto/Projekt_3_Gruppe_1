package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.Car;
import dk.ek.bilabonnement2026.model.CarOverview;
import dk.ek.bilabonnement2026.repository.CarRepository;
import dk.ek.bilabonnement2026.repository.RentalContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        validatorCar = carRepository.findCarByLicensePlate(car.getLicensePlate());
        if(validatorCar != null){
            throw new IllegalArgumentException("En bil med denne nummerplade eksisterer allerede");
        }

        if(car.getLicensePlate().length() != 7){
            throw new IllegalArgumentException("Nummerpladen skal være 7 lang");
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

    public boolean changeCarStatusToExpired(int carId){
        Car car = carRepository.findCarByCarNumber(carId);

        if (rentalContractRepository.findRentalContractByCarId(carId) != null){ // make sure no active rental agreement is in place
            return false;
        }

        if (!car.getStatus().equalsIgnoreCase("Ledig")) { // check car status to make sure it's not already rented
             return false;
        }

        carRepository.updateCarStatus(carId, "Udgået");
        return true;
    }

    public Car getCarByCarId(int carId){
        return carRepository.findCarByCarNumber(carId);
    }

    public void updateCar(Car car) {
        carRepository.updateCar(car);
    }

    public Car getCarByLicensePlate(String licensePlate) {
        return carRepository.findCarByLicensePlate(licensePlate);
    }
}
