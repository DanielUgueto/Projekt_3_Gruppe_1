package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.Car;
import dk.ek.bilabonnement2026.model.RentalContract;
import dk.ek.bilabonnement2026.repository.CarRepository;
import dk.ek.bilabonnement2026.repository.RentalContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalContractService {

    @Autowired
    CarRepository carRepository;
    @Autowired
    RentalContractRepository rentalContractRepository;

    public void createRentalContract(RentalContract rentalContract){

        Car car = carRepository.findCarByCarNumber(rentalContract.getCarId());
        if(car == null){
            throw new IllegalArgumentException("Bilen eksistere ikke");
        }

        RentalContract existingContract = rentalContractRepository.findRentalContractByCarId(rentalContract.getCarId());
        if(existingContract != null){
            throw new IllegalArgumentException("Bilen har allerede en aktiv lejeaftale");
        }

        if(!rentalContract.getEndDate().isAfter(rentalContract.getStartDate())){
            throw new IllegalArgumentException("Slutdato skal være efter startdato");
        }

        rentalContractRepository.saveRentalContract(rentalContract);

        car.setStatus("Udlejet");
        carRepository.updateCarStatus(car);
    }
}
