package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.DamageCategory;
import dk.ek.bilabonnement2026.repository.DamageCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageCategoryService {

    @Autowired
    DamageCategoryRepository damageCategoryRepository;

    public List<DamageCategory> getAllDamageCategories(){
        return damageCategoryRepository.getAllDamageCategories();
    }
}
