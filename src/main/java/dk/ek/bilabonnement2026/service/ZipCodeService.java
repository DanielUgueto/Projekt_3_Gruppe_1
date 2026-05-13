package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.repository.ZipCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZipCodeService {

    @Autowired
    ZipCodeRepository zipCodeRepository;

    public boolean zipcodeExists(String zipcode){
        return zipCodeRepository.zipcodeExists(zipcode);
    }
}
