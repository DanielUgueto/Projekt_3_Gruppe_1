package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.ZipCode;
import dk.ek.bilabonnement2026.repository.ZipCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZipCodeService {

    @Autowired
    ZipCodeRepository zipCodeRepository;

    public List<ZipCode> getAllZipCodes(){
        return zipCodeRepository.getAll();
    }
}
