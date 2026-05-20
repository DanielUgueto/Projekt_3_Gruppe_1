package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
//Rune
@SpringBootTest
@ActiveProfiles("test")
public class CarRepositoryIntegrationTest {

    @Autowired
    CarRepository carRepository;
    //Rune
    @Test
    @DisplayName("findCarByVinNumber() Happy flow")
    public void findCarByVinNumberHappyFlow(){
        //Preconditions
        String vinNumber = "MB100000000000001";

        //Execution
        Car car = carRepository.findCarByVinNumber(vinNumber);

        //Postconditions
        assertNotNull(car, "Bilen blev ikke fundet i databasen");
        assertEquals(vinNumber, car.getVinNumber());
    }
    //Rune
    @Test
    @DisplayName("findCarByVinNumber() Exception flow")
    public void findCarByVinNumberExceptionFlow(){
        //Preconditions
        String vinNumber ="XX300000000000003";

        //Execution
        Car car = carRepository.findCarByVinNumber(vinNumber);

        //Postconditions
        assertNull(car);
    }
}
