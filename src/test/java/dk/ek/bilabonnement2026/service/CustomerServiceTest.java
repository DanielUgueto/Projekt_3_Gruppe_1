package dk.ek.bilabonnement2026.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerServiceTest {

    private final CustomerService service = new CustomerService();
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
