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
}

