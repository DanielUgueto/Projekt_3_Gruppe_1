package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.Car;
import dk.ek.bilabonnement2026.model.RentalContract;
import dk.ek.bilabonnement2026.repository.CarRepository;
import dk.ek.bilabonnement2026.repository.RentalContractRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RentalContractServiceTest {
//
//    @Mock
//    CarRepository carRepository;
//    @Mock
//    RentalContractRepository rentalContractRepository;
//
//    @InjectMocks
//    RentalContractService rentalContractService;
//
//    @Test
//    public void registerReturnOfCarHappyFlow(){
//        //PreConditions
//        Car car = new Car(1, 1, "WBA12345678901234", "AB12345",
//                3000.0, "Udlejet", "Sort");
//        RentalContract contract = new RentalContract(1, 1, 1, 1,
//                LocalDate.now().minusMonths(6),
//                LocalDate.now().minusMonths(1),
//                "København", "Aktiv", "Unlimited");
//
//        when(carRepository.findCarByCarNumber(1)).thenReturn(car);
//        when(rentalContractRepository.findRentalContractByCarId(1)).thenReturn(contract);
//
//        //Executions
//        rentalContractService.registerReturnOfCar(1);
//
//        //PostConditions
//        verify(carRepository, times(1)).updateCarStatus(car);
//    }
//
//    @Test
//    public void registerReturnOfCarExceptionFlow(){
//        //PreConditions
//        Car car = new Car(1, 1, "WBA12345678901234", "AB12345",
//                3000.0, "Ledig", "Sort");
//
//        when(carRepository.findCarByCarNumber(1)).thenReturn(car);
//        //Executions
//
//        //PostConditions
//        assertThrows(IllegalArgumentException.class,() -> rentalContractService.registerReturnOfCar(1));
//    }

}
