package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.Car;
import dk.ek.bilabonnement2026.model.CarOverview;
import dk.ek.bilabonnement2026.model.RentalContract;
import dk.ek.bilabonnement2026.model.RentalContractOverview;
import dk.ek.bilabonnement2026.repository.CarRepository;
import dk.ek.bilabonnement2026.repository.CustomerRepository;
import dk.ek.bilabonnement2026.repository.RentalContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentalContractService {

    @Autowired
    CarRepository carRepository;
    @Autowired
    RentalContractRepository rentalContractRepository;
    @Autowired
    CustomerRepository customerRepository;

    public void createRentalContract(RentalContract rentalContract) {

        Car car = carRepository.findCarByCarNumber(rentalContract.getCarId());
        if (car == null) {
            throw new IllegalArgumentException("Bilen eksistere ikke");
        }

        RentalContract existingContract = rentalContractRepository.findRentalContractByCarId(rentalContract.getCarId());
        if (existingContract != null) {
            throw new IllegalArgumentException("Bilen har allerede en aktiv lejeaftale");
        }

        if (!rentalContract.getEndDate().isAfter(rentalContract.getStartDate())) {
            throw new IllegalArgumentException("Slutdato skal være efter startdato");
        }

        rentalContractRepository.saveRentalContract(rentalContract);

        carRepository.updateCarStatus(car.getCarId(), "Udlejet");
    }

    public void updateRentalContract(RentalContract rentalContract) {
        RentalContract existingContract = rentalContractRepository.findRentalContractById(rentalContract.getRentalContractId());
        if (existingContract == null) {
            throw new IllegalArgumentException("Lejekontrakt med id " + rentalContract.getRentalContractId() + " findes ikke i systemet");
        }

        Car car = carRepository.findCarByCarNumber(existingContract.getCarId());

        if (car == null) {
            throw new IllegalArgumentException("Bil tilhørende lejekontrakten kunne ikke findes");
        }
        if (!car.getStatus().equalsIgnoreCase("Udlejet")) {
            throw new IllegalArgumentException("Bilen er ikke udlejet");
        }

        if (!existingContract.getStatus().equalsIgnoreCase("Aktiv")) {
            throw new IllegalArgumentException("Lejekontrakten er ikke aktiv");
        }

        if (!rentalContract.getEndDate().isAfter(rentalContract.getStartDate())) {
            throw new IllegalArgumentException("Slutdato skal være efter startdato");
        }

        rentalContractRepository.updateRentalContract(rentalContract);
    }

    public void registerReturnOfCar(int carId) {

        Car car = carRepository.findCarByCarNumber(carId);
        if (car == null) {
            throw new IllegalArgumentException("Bilen eksistere ikke");
        }

        if (!car.getStatus().equals("Udlejet")) {
            throw new IllegalArgumentException("Bilen er ikke udlejet");
        }

        RentalContract existingContract = rentalContractRepository.findRentalContractByCarId(carId);
        if (existingContract == null) {
            throw new IllegalArgumentException("Der eksistere ikke nogen lejeaftale.");
        }

        carRepository.updateCarStatus(car.getCarId(), "Tilbageleveret");
        rentalContractRepository.updateRentalContractStatus(existingContract.getRentalContractId(), "Afsluttet");

        RentalContract otherActiveContracts = rentalContractRepository.findActiveContractByCustomerId(existingContract.getCustomerId());
        if (otherActiveContracts == null) {
            customerRepository.setCustomerStatusInactive(existingContract.getCustomerId());
        }

    }

    // Nico
    public double calculateMonthlyRevenue() {
        return rentalContractRepository.calculateMonthlyRevenue();
    }

    public double projectedYearlyRevenue() {
        return rentalContractRepository.projectedYearlyRevenue();
    }

    public RentalContract getRentalContractByContractId(int contractId) {
        RentalContract rentalContract = rentalContractRepository.findRentalContractById(contractId);
        return rentalContract;
    }

    public List<CarOverview> getReturnedCarsWithContractByFilter(String filter) {
        List<CarOverview> allCars = rentalContractRepository.findReturnedCarsWithContract();
        List<CarOverview> filteredCars = new ArrayList<>();

        for (CarOverview car : allCars) {
            if (filter.equals("afventer") && car.getStatus().equals("Tilbageleveret")) {
                filteredCars.add(car);
            } else if (filter.equals("udførte") && car.getStatus().equals("Klar til transport")) {
                filteredCars.add(car);

            }
        }
        return filteredCars;
    }

    public List<RentalContractOverview> getAllRentalContractOverviews(String statusFilter) {
        return rentalContractRepository.findAllRentalContractOverviews(statusFilter);
    }

    public RentalContractOverview getRentalContractOverviewById(int contractId) {
        return rentalContractRepository.findRentalContractOverviewById(contractId);
    }

    // Nico
    public int returnAmountOfContractsByStatus(String status) {
        return rentalContractRepository.returnAmountOfContractsByStatus(status);
    }
}
