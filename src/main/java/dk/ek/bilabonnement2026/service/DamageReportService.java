package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.*;
import dk.ek.bilabonnement2026.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DamageReportService {

    @Autowired
    DamageReportRepository damageReportRepository;

    @Autowired
    RentalContractRepository rentalContractRepository;

    @Autowired
    DamageRepository damageRepository;
    @Autowired
    CarRepository carRepository;


    public void createDamageReport(int rentalContractId, int employeeId, String description, List<DamageCategory> selectedDamages) {

        //Get rental contract from the DB and validate that it exists.
        RentalContract rentalContract = rentalContractRepository.findRentalContractById(rentalContractId);
        if (rentalContract == null) {
            throw new IllegalArgumentException("Lejeaftalen findes ikke");
        }

        //Validate that the car is returned.
        if (!"Afsluttet".equals(rentalContract.getStatus())) {
            throw new IllegalArgumentException("Bilen er ikke tilbageleveret");
        }

        //Validate that there isnt already a damage report for the current contract.
        DamageReport existing = damageReportRepository.findDamageReportByRentalContractId(rentalContractId);
        if (existing != null) {
            throw new IllegalArgumentException("Der findes allerede en skadesrapport på denne lejeaftale");
        }

        //Calculate the total price, by running through the chosen damage categories from the session.
        double totalPrice = 0;
        for (DamageCategory category : selectedDamages) {
            totalPrice += category.getStandardPrice();
        }

        //Save the report, creates a damagereport object with the totalprice and saves it.
        DamageReport damageReport = new DamageReport(
                rentalContractId, employeeId, LocalDate.now(), totalPrice, description
        );
        damageReportRepository.saveDamageReport(damageReport);

        //Gets the saved report from the DB, since we need the id.
        DamageReport savedReport = damageReportRepository.findDamageReportByRentalContractId(rentalContractId);

        //Get the car with carId from the contract and change the cars status.
        Car car = carRepository.findCarByCarNumber(rentalContract.getCarId());
        car.setStatus("Klar til transport");
        carRepository.updateCarStatus(car);

        //Save the individual damages and save them in the DB with references to the saved report.
        for (DamageCategory category : selectedDamages) {
            damageRepository.save(new Damage(savedReport.getDamageReportId(), category.getDamageCategoryId()));
        }
    }
}