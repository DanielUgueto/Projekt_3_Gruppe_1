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

    public List<Customer> getAllCustomers(){
        return customerRepository.getAllCustomers();
    }

    public Customer getCustomerByCustomerId(int customerId){
        return customerRepository.findCustomerByCustomerId(customerId);
    }

    public CustomerAddress getCustomerAddressByCustomerId(int customerId){
        return customerAddressRepository.findCustomerAddressByCustomerId(customerId);
    }

    public String updateCustomer(Customer customer, CustomerAddress customerAddress){
        //tjekke kunden er der
        Customer existingCustomer = customerRepository.findCustomerByCustomerId(customer.getCustomerId());
        if(existingCustomer == null){
            return "Kunden eksisterer ikke";
        }
        //tjekker om email matcher pattern
        if(!customer.getEmail().matches(EMAIL_PATTERN)){
            return "Email har ugyldigt format";
        }
        //tjekker først om kunden har ændret email og først hvis de har tjekkes om den er unik i systemet.
        if(!existingCustomer.getEmail().equals(customer.getEmail())){
            if(customerRepository.findCustomerByCustomerEmail(customer.getEmail()) != null){
                return "Email skal være unik";
            }
        }

        if(!existingCustomer.getCprNumber().equals(customer.getCprNumber())){
            if(customerRepository.findCustomerByCustomerCprNumber(customer.getCprNumber()) != null){
                return "Cpr nummer eksisterer allerede i systemet";
            }
        }

        if(existingCustomer.getPhoneNumber() != customer.getPhoneNumber()){
            if(customerRepository.findCustomerByCustomerPhoneNumber(customer.getPhoneNumber()) != null){
                return "Mobilnummeret er taget af en anden kunde";
            }
        }

        // der må godt være flere kunder på en adresse. fix
        CustomerAddress existingAddress = customerAddressRepository.findCustomerAddressByCustomerId(customerAddress.getCustomerId());
        if(existingAddress == null){
            return "Adressen eksisterer ikke";
        }

        customerRepository.updateCustomer(customer);
        customerAddressRepository.updateCustomerAddress(customerAddress);
        return null;
    }
}

