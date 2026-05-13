package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Customer;
import dk.ek.bilabonnement2026.model.CustomerAddress;
import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.service.CustomerService;
import dk.ek.bilabonnement2026.service.EmployeeService;
import dk.ek.bilabonnement2026.service.ZipCodeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;



@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    ZipCodeService zipCodeService;
    @Autowired
    EmployeeService employeeService;


    @GetMapping("/customer/register")
    public String showRegisterCustomer(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        return "register-customer";
    }

    @PostMapping("/customer/register")
    public String registerCustomer(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("driversLicenseNumber") String driversLicenseNumber,
            @RequestParam("cprNumber") String cprNumber,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("streetName") String streetName,
            @RequestParam("houseNumber") String houseNumber,
            @RequestParam("floor") String floor,
            @RequestParam("zipCode") String zipCode,
            Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        if (!zipCodeService.zipcodeExists(zipCode)) {
            model.addAttribute("wrongZipcode", "Postnummer ikke fundet");
            return "register-customer";
        }

        Customer customer = new Customer(firstName, lastName, driversLicenseNumber, cprNumber, email, phoneNumber);
        CustomerAddress customerAddress = new CustomerAddress(0, zipCode, streetName, houseNumber, floor);
        String error = customerService.registerCustomer(customer, customerAddress);

        if (error != null) {
            model.addAttribute("error", error);
            return "register-customer";
        }

        return employeeService.redirectByRole(employee);
    }

    @GetMapping("/customer/edit")
    public String showEditCustomer(@RequestParam int customerId,
                                   HttpSession session,
                                   Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }
        Customer customer = customerService.getCustomerByCustomerId(customerId);
        if (customer == null) {
            return employeeService.redirectByRole(employee);
        }
        CustomerAddress customerAddress = customerService.getCustomerAddressByCustomerId(customerId);
        if (customerAddress == null) {
            return employeeService.redirectByRole(employee);
        }

        model.addAttribute("selectedCustomer", customer);
        model.addAttribute("selectedCustomerAddress", customerAddress);

        return "edit-customer";
    }

    @PostMapping("/customer/edit")
    public String updateCustomer(@RequestParam int customerId,
                                 @RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName,
                                 @RequestParam("email") String email,
                                 @RequestParam("phoneNumber") String phoneNumber,
                                 @RequestParam("cprNumber") String cprNumber,
                                 @RequestParam("zipCode") String zipCode,
                                 @RequestParam("streetName") String streetName,
                                 @RequestParam("houseNumber") String houseNumber,
                                 @RequestParam("floor") String floor,
                                 HttpSession session,
                                 Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }
        Customer customer = new Customer(customerId, firstName, lastName, "", cprNumber, email, phoneNumber);
        CustomerAddress customerAddress = new CustomerAddress(customerId, zipCode, streetName, houseNumber, floor);

        if (!zipCodeService.zipcodeExists(zipCode)) {
            model.addAttribute("wrongZipcode", "Postnummer ikke fundet");
            model.addAttribute("selectedCustomer", customer);
            model.addAttribute("selectedCustomerAddress", customerAddress);
            return "edit-customer";
        }

        String error = customerService.updateCustomer(customer, customerAddress);
        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("selectedCustomer", customer);
            model.addAttribute("selectedCustomerAddress", customerAddress);
            return "edit-customer";
        }

        return employeeService.redirectByRole(employee);
    }
}