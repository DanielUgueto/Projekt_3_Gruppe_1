package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Customer;
import dk.ek.bilabonnement2026.model.CustomerAddress;
import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.model.ZipCode;
import dk.ek.bilabonnement2026.service.CustomerService;
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


    @GetMapping("/customer/register")
    public String showRegisterCustomer(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        List<ZipCode> zipCodes = zipCodeService.getAllZipCodes();
        model.addAttribute("zipCodes", zipCodes);

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

        Customer customer = new Customer(firstName, lastName, driversLicenseNumber, cprNumber, email, phoneNumber);
        CustomerAddress customerAddress = new CustomerAddress(0,zipCode,streetName,houseNumber,floor);
        String error = customerService.registerCustomer(customer,customerAddress);

        if (error != null) {
            List<ZipCode> zipCodes = zipCodeService.getAllZipCodes();
            model.addAttribute("error", error);
            model.addAttribute("zipCodes",zipCodes);
            return "register-customer";
        }

        return "redirect:/index";
    }
}