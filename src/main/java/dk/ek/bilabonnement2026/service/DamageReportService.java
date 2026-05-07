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

        RentalContract rentalContract = rentalContractRepository.findRentalContractById(rentalContractId);
        if (rentalContract == null) {
            throw new IllegalArgumentException("Lejeaftalen findes ikke");
        }

        if (!"Afsluttet".equals(rentalContract.getStatus())) {
            throw new IllegalArgumentException("Bilen er ikke tilbageleveret");
        }

        DamageReport existing = damageReportRepository.findDamageReportByRentalContractId(rentalContractId);
        if (existing != null) {
            throw new IllegalArgumentException("Der findes allerede en skadesrapport på denne lejeaftale");
        }

        double totalPrice = 0;
        for (DamageCategory category : selectedDamages) {
            totalPrice += category.getStandardPrice();
        }

        DamageReport damageReport = new DamageReport(
                rentalContractId, employeeId, LocalDate.now(), totalPrice, description
        );
        damageReportRepository.saveDamageReport(damageReport);

        DamageReport savedReport = damageReportRepository.findDamageReportByRentalContractId(rentalContractId);

        Car car = carRepository.findCarByCarNumber(rentalContract.getCarId());
        car.setStatus("Klar til transport");
        carRepository.updateCarStatus(car);

        for (DamageCategory category : selectedDamages) {
            damageRepository.save(new Damage(savedReport.getDamageReportId(), category.getDamageCategoryId()));
        }
    }
}