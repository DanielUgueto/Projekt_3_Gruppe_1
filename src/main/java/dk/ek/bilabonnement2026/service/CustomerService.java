package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.Customer;
import dk.ek.bilabonnement2026.model.CustomerAddress;
import dk.ek.bilabonnement2026.model.RentalContract;
import dk.ek.bilabonnement2026.repository.CustomerAddressRepository;
import dk.ek.bilabonnement2026.repository.CustomerRepository;
import dk.ek.bilabonnement2026.repository.RentalContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private static final String EMAIL_PATTERN = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_PATTERN = "^(\\+45)?[\\s-]?(\\d{2}[\\s-]?){3}\\d{2}$";
    private static final String DRIVERS_LICENSE_PATTERN = "^\\d{8}$";
    private static final String CPR_PATTERN = "^\\d{6}-?\\d{4}$";

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerAddressRepository customerAddressRepository;
    @Autowired
    RentalContractRepository rentalContractRepository;

    public String registerCustomer(Customer customer, CustomerAddress address) {

        if (customerRepository.findCustomerByCustomerEmail(customer.getEmail()) != null) {
            return "Email skal være unik!";
        }

        customerRepository.createCustomer(customer);
        Customer savedCustomer = customerRepository.findCustomerByCustomerEmail(customer.getEmail());
        address.setCustomerId(savedCustomer.getCustomerId());
        customerAddressRepository.saveCustomerAddress(address);
        return null;
    }

    public String setCustomerStatusInactive(int customerId) {
        RentalContract activeContract = rentalContractRepository.findActiveContractByCustomerId(customerId);
        if (activeContract != null) {
            return "Afslut lejeaftale før kunde kan slettes";
        }
        customerRepository.setCustomerStatusInactive(customerId);
        return null;
    }

    public String setCustomerStatusActive(int customerId) {
        customerRepository.setCustomerStatusActive(customerId);
        return null;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    public List<Customer> getAllActiveCustomers() {
        return customerRepository.getAllActiveCustomers();
    }

    public List<Customer> getAllInactiveCustomers() {
        return customerRepository.getAllInactiveCustomers();
    }

    //Rune
    public Customer getCustomerByCustomerId(int customerId) {
        return customerRepository.findCustomerByCustomerId(customerId);
    }

    //Rune
    public CustomerAddress getCustomerAddressByCustomerId(int customerId) {
        return customerAddressRepository.findCustomerAddressByCustomerId(customerId);
    }

    //Rune
    public String updateCustomer(Customer customer, CustomerAddress customerAddress) {
        //tjekke kunden er der
        Customer existingCustomer = customerRepository.findCustomerByCustomerId(customer.getCustomerId());
        if (existingCustomer == null) {
            return "Kunden eksisterer ikke";
        }
        //tjekker om email matcher pattern
        if (!customer.getEmail().matches(EMAIL_PATTERN)) {
            return "Email har ugyldigt format";
        }
        //tjekker først om kunden har ændret email og først hvis de har tjekkes om den er unik i systemet.
        if (!existingCustomer.getEmail().equals(customer.getEmail())) {
            if (customerRepository.findCustomerByCustomerEmail(customer.getEmail()) != null) {
                return "Email skal være unik";
            }
        }

        if (!existingCustomer.getCprNumber().equals(customer.getCprNumber())) {
            if (customerRepository.findCustomerByCustomerCprNumber(customer.getCprNumber()) != null) {
                return "Cpr nummer eksisterer allerede i systemet";
            }
        }

        if (!existingCustomer.getPhoneNumber().equals(customer.getPhoneNumber())) {
            if (customerRepository.findCustomerByCustomerPhoneNumber(customer.getPhoneNumber()) != null) {
                return "Mobilnummeret er taget af en anden kunde";
            }
        }


        CustomerAddress existingAddress = customerAddressRepository.findCustomerAddressByCustomerId(customerAddress.getCustomerId());
        if (existingAddress == null) {
            return "Adressen eksisterer ikke";
        }

        customerRepository.updateCustomer(customer);
        customerAddressRepository.updateCustomerAddress(customerAddress);
        return null;
    }

    //Daniel
    public List<Customer> searchCustomerByName(String query) {
        //returnerer tom list med det samme
        if (query == null || query.isBlank()) {
            return List.of();
        }
        query = query.trim();

        return customerRepository.findCustomerByName(query.trim());
    }

//Validerings metoder
    //Rune
    public boolean isValidPhoneNumber(String number) {
        if (number == null || number.isBlank()) {
            return false;
        }
        return number.trim().matches(PHONE_PATTERN);
    }

    //Rune
    public boolean isValidCpr(String cpr) {
        if (cpr == null || cpr.isBlank()) {
            return false;
        }
        return cpr.trim().matches(CPR_PATTERN);
    }

    //Rune
    public boolean isValidDriversLicense(String licenseNumber) {
        if (licenseNumber == null || licenseNumber.isBlank()) {
            return false;
        }
        return licenseNumber.trim().matches(DRIVERS_LICENSE_PATTERN);
    }


    // normalisering

    //Rune
    public String normalizePhoneNumber(String number) {
        String numberOnlyDigits = number.replaceAll("\\D", "");
        if (numberOnlyDigits.startsWith("45") && numberOnlyDigits.length() == 10) {
            numberOnlyDigits = numberOnlyDigits.substring(2);
        }
        return numberOnlyDigits.trim();
    }

    //Rune
    public String normalizeDriversLicense(String licenseNumber) {
        return licenseNumber.trim();
    }

    //Rune
    public String normalizeCpr(String cpr) {
        return cpr.replaceAll("-", "").trim();
    }
}