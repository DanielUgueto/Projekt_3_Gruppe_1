package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.*;
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

import java.util.List;


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
            @RequestParam("driversLicenseNumber") int driversLicenseNumber,
            @RequestParam("cprNumber") String cprNumber,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") int phoneNumber,
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
        CustomerAddress customerAddress = new CustomerAddress(0,zipCode,streetName,houseNumber,floor);
        String error = customerService.registerCustomer(customer,customerAddress);

        if (error != null) {
            model.addAttribute("error", error);
            return "register-customer";
        }

        return employeeService.redirectByRole(employee);
    }

    @PostMapping("/customer/delete")
    public String deleteCustomer(@RequestParam("customerId") int customerId,
                                 HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }
        customerService.setCustomerStatusInactive(customerId);
        return "redirect:/customer/dashboard";
    }

    @GetMapping("/customer/dashboard")
    public String showCustomerDashboard(@RequestParam(required = false) String status,
                                   @RequestParam(required = false) Integer customerId,
                                   HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null){
            return "redirect:/";
        }

        List<Customer> list = customerService.getAllCustomers();

        if (customerId != null) {
            Customer selectedCustomer = null;
            for (Customer c : list){
                if (c.getCustomerId() == customerId) {
                    selectedCustomer = c;
                    break;
                }
            }
            model.addAttribute("selectedCustomer", selectedCustomer);
        }

        model.addAttribute("customerList", list);

        return "customer-dashboard";
    }


}