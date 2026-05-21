package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.DamageCategory;
import dk.ek.bilabonnement2026.repository.DamageCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//Rune
@Service
public class DamageCategoryService {

    @Autowired
    DamageCategoryRepository damageCategoryRepository;

    public List<DamageCategory> getAllActiveDamageCategories() {
        return damageCategoryRepository.getAllActiveDamageCategories();
    }

    public DamageCategory getDamageCategoryById(int damageCategoryId) {
        return damageCategoryRepository.findById(damageCategoryId);
    }

    public String createDamageCategory(DamageCategory category) {
        String error = validateInput(category.getName(), category.getStandardPrice());
        if (error != null) {
            return error;
        }

        if (damageCategoryRepository.findDamageCategoryByName(category.getName().trim()) != null) {
            return "Der findes allerede en skadekategori med dette navn";
        }

        damageCategoryRepository.saveDamageCategory(category);
        return null;
    }

    public String updateDamageCategory(DamageCategory category) {
        DamageCategory existing = damageCategoryRepository.findById(category.getDamageCategoryId());
        if (existing == null) {
            return "Skadekategorien findes ikke i systemet";
        }

        String error = validateInput(category.getName(), category.getStandardPrice());
        if (error != null) {
            return error;
        }

        String newName = category.getName().trim();
        if (!existing.getName().equals(newName)) {
            if (damageCategoryRepository.findDamageCategoryByName(newName) != null) {
                return "Der findes allerede en skadekategori med dette navn";
            }
        }

        damageCategoryRepository.updateDamageCategory(category);
        return null;
    }

    public String setDamageCategoryInactive(int damageCategoryId) {
        DamageCategory existing = damageCategoryRepository.findById(damageCategoryId);
        if (existing == null) {
            return "Skadekategorien findes ikke";
        }
        if (!existing.getIsActive()) {
            return "Skadekategorien er allerede deaktiveret";
        }

        damageCategoryRepository.updateDamageCategoryIsActive(damageCategoryId, false);
        return null;
    }

    private String validateInput(String name, double standardPrice) {

        if (name == null || name.trim().isEmpty()) {
            return "Navn må ikke være tomt";
        }

        if (standardPrice <= 0) {
            return "Prisen skal være større end 0";
        }

        return null;
    }
}
