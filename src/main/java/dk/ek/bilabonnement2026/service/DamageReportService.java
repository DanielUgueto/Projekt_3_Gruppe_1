package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.DamageReport;
import dk.ek.bilabonnement2026.model.RentalContract;
import dk.ek.bilabonnement2026.repository.DamageReportRepository;
import dk.ek.bilabonnement2026.repository.RentalContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DamageReportService {

    @Autowired
    DamageReportRepository damageReportRepository;

    @Autowired
    RentalContractRepository rentalContractRepository;


    public void createDamageReport(int rentalContractId, int employeeId, double totalPrice, String description) {

        RentalContract rentalContract = rentalContractRepository.findRentalContractById(rentalContractId);
        if (rentalContract == null) {
            throw new IllegalArgumentException("Lejeaftalen findes ikke");
        }

        if (!"Afleveret".equals(rentalContract.getStatus())) {
            throw new IllegalArgumentException("Bil er ikke afleveret");
        }

        DamageReport damageReport = damageReportRepository.findDamageReportByRentalContractId(rentalContractId);
        if (damageReport != null) {
            throw new IllegalArgumentException("Der findes allerede en skadesrapport på denne lejeaftale");
        }

        damageReport = new DamageReport(
                rentalContractId, employeeId, LocalDate.now(), totalPrice, description
        );

        damageReportRepository.saveDamageReport(damageReport);
    }
}