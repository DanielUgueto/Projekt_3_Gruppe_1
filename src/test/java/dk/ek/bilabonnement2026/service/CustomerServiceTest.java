package dk.ek.bilabonnement2026.service;


import dk.ek.bilabonnement2026.repository.CustomerAddressRepository;
import dk.ek.bilabonnement2026.repository.CustomerRepository;
import dk.ek.bilabonnement2026.repository.RentalContractRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerAddressRepository customerAddressRepository;
    @Mock
    private RentalContractRepository rentalContractRepository;

    @InjectMocks
    private CustomerService service;
    //Rune
    @Test
    @DisplayName("isValidPhoneNumber() happy flow: er gyldigt nummer")
    void isValidPhoneNumberHappyFlow(){
        String validNumber = "+45 12345678";

        boolean result = service.isValidPhoneNumber(validNumber);

        assertTrue(result,"Et gyldigt dansk nummer skal accepteres");
    }
    //Rune
    @Test
    @DisplayName("isValidPhoneNumber() exception flow: nummeret er ikke gyldigt")
    void isValidPhoneNumberExceptionFlow(){
        String invalidNumber = "+45 1234567a";

        boolean result = service.isValidPhoneNumber(invalidNumber);

        assertFalse(result,"Et ugyldigt nummer skal afvises");
    }
}
