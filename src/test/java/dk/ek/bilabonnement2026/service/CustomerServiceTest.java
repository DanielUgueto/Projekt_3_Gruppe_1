package dk.ek.bilabonnement2026.service;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerServiceTest {

    private final CustomerService service = new CustomerService();

    @Test
    void isValidPhoneNumber_happyFlow(){
        String validNumber = "+45 12345678";

        boolean result = service.isValidPhoneNumber(validNumber);

        assertTrue(result,"Et gyldigt dansk nummer skal accepteres");
    }

    @Test
    void isValidPhoneNumber_exceptionFlow(){
        String invalidNumber = "+45 1234567a";

        boolean result = service.isValidPhoneNumber(invalidNumber);

        assertFalse(result,"Et ugyldigt nummer skal afvises");
    }
}
