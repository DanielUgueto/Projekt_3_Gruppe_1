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
            throw new IllegalArgumentException("Nummerpladen skal være 7 tegn");
        }
        if(car.getVinNumber().length() != 17){
            throw new IllegalArgumentException("Stelnummeret skal være 17 tegn");
        }

        carRepository.saveCar(car);
    }

    // Nico
    public List<CarOverview> findCarsWithDetails() {
        return carRepository.findAllCarsWithDetails();
    }

    // Nico
    public List<CarOverview> findCarsWithDetailsByStatus(String status){
        return carRepository.findAllCarsWithDetailsByStatus(status);
    }

    public List<Car> findCarsByStatus(String status){
        return carRepository.findCarsByStatus(status);
    }

    // Nico
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

    // Nico
    public Car getCarByCarId(int carId){
        return carRepository.findCarByCarNumber(carId);
    }

    // Nico
    public void updateCar(Car car) {
        carRepository.updateCar(car);
    }

    // Nico
    public Car getCarByLicensePlate(String licensePlate) {
        return carRepository.findCarByLicensePlate(licensePlate);
    }

    // Nico
    public int returnCarAmountByStatus(String status) {
        return carRepository.returnCarAmountByStatus(status);
    }

    // Nico
    public boolean changeCarStatusToAvailable(int carId) {
        // this method could definitely be combined with changeCarStatusToExpired
        // but I wont
        Car car = carRepository.findCarByCarNumber(carId);

        if (!car.getStatus().equalsIgnoreCase("Klar til transport")) { // check car status to make sure it's not already rented
            return false;
        }

        carRepository.updateCarStatus(carId, "Ledig");
        return true;
    }
}
