package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.Damage;
import dk.ek.bilabonnement2026.model.DamageCategory;
import dk.ek.bilabonnement2026.model.DamageReport;
import dk.ek.bilabonnement2026.model.RentalContract;
import dk.ek.bilabonnement2026.repository.DamageCategoryRepository;
import dk.ek.bilabonnement2026.repository.DamageReportRepository;
import dk.ek.bilabonnement2026.repository.DamageRepository;
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

    @Autowired
    DamageCategoryRepository damageCategoryRepository;

    @Autowired
    DamageRepository damageRepository;


    public void createDamageReport(int rentalContractId, int employeeId, double totalPrice, String description) {

        RentalContract rentalContract = rentalContractRepository.findRentalContractById(rentalContractId);
        if (rentalContract == null) {
            throw new IllegalArgumentException("Lejeaftalen findes ikke");
        }

        if (!"Tilbageleveret".equals(rentalContract.getStatus())) {
            throw new IllegalArgumentException("Bil er ikke tilbageleveret");
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

    public void registrerDamage(int damageReportId, int damageCategoryId){

        DamageReport damageReport = damageReportRepository.findDamageReportByRentalContractId(damageReportId);
        if(damageReport == null){
            throw new IllegalArgumentException("Skadesrapporten findes ikke");
        }

        DamageCategory category = damageCategoryRepository.findById(damageCategoryId);
        if(category == null){
            throw new IllegalArgumentException("Skadekategorien findes ikke");
        }

        Damage damage = new Damage(damageReportId, damageCategoryId);
        damageRepository.save(damage);

        double newTotal = damageReport.getTotalPrice() + category.getStandardPrice();
        damageReportRepository.updateTotalPrice(damageReportId,newTotal);
    }
}