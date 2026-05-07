package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.Customer;
import dk.ek.bilabonnement2026.model.CustomerAddress;
import dk.ek.bilabonnement2026.repository.CustomerAddressRepository;
import dk.ek.bilabonnement2026.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerAddressRepository customerAddressRepository;

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
}

