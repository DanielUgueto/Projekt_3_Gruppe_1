package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.Car;
import dk.ek.bilabonnement2026.repository.CarRepository;
import dk.ek.bilabonnement2026.repository.RentalContractRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private RentalContractRepository rentalContractRepository;

    @InjectMocks
    private CarService carService;

    @Test
    @DisplayName("createCar() Happy flow: bil med gyldige data gemmes")
    public void createCarHappyFlow(){
        //Preconditions
        Car car = new Car(
                1, //carModelId
                "WBA12345678901234", //17 tegn gyldig VIN/stelnummer
                "AB12345", //7 tegn gyldig nummerplade
                3500.0, // monthlyPrice
                "Ledig", // status
                "Sort", //colour
                LocalDate.now() //registrationDate
        );

        //unik tjek retunerer null hvis bilen ikke findes i forvenen
        given(carRepository.findCarByVinNumber(car.getVinNumber())).willReturn(null);
        given(carRepository.findCarByLicensePlate(car.getLicensePlate())).willReturn(null);


        //Execution
        carService.createCar(car);


        //Postconditions
        verify(carRepository, times(1)).saveCar(car);
    }

    @Test
    @DisplayName("createCar() exception flow: stelnummer findes allerede")
    public void createCarExceptionFlow(){
        //Preconditions
        Car newCar = new Car(
                1,
                "WBA12345678901234",
                "AB12345",
                3500.0,
                "Ledig",
                "Sort",
                LocalDate.now());

        Car existingCar = new Car(
                99,
                1,
                "WBA12345678901234",
                "XY98765",
                3500.0,
                "Udlejet",
                "Hvid",
                LocalDate.now().minusMonths(2));

        //En anden bil har allerede det stelnummer
        given(carRepository.findCarByVinNumber(newCar.getVinNumber())).willReturn(existingCar);


        //Execution
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> carService.createCar(newCar));


        //Postconditions
        assertEquals("En bil med dette stelnummer eksistere allerede", thrown.getMessage());
        //Vigtig at saveCar() aldrig bliver kaldt ved en exeception.
        verify(carRepository, never()).saveCar(newCar);
    }
}
