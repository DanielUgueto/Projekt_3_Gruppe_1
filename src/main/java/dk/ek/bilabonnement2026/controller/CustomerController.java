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

        if (!customerService.isValidPhoneNumber(phoneNumber)) {
            model.addAttribute("error", "Ugyldigt telefonnummer");
            addFormDataToCustomerModel(model, firstName, lastName,
                    driversLicenseNumber, cprNumber, email,
                    phoneNumber, streetName, houseNumber, floor, zipCode);
            return "register-customer";
        }
        if (!customerService.isValidCpr(cprNumber)) {
            model.addAttribute("error", "Ugyldigt CPR-nummer");
            addFormDataToCustomerModel(model, firstName, lastName,
                    driversLicenseNumber, cprNumber, email,
                    phoneNumber, streetName, houseNumber, floor, zipCode);
            return "register-customer";
        }
        if (!customerService.isValidDriversLicense(driversLicenseNumber)) {
            model.addAttribute("error", "Ugyldigt Kørekort nummer");
            addFormDataToCustomerModel(model, firstName, lastName,
                    driversLicenseNumber, cprNumber, email,
                    phoneNumber, streetName, houseNumber, floor, zipCode);
            return "register-customer";
        }

        if (!zipCodeService.zipcodeExists(zipCode)) {
            model.addAttribute("wrongZipcode", "Postnummer ikke fundet");
            addFormDataToCustomerModel(model, firstName, lastName,
                    driversLicenseNumber, cprNumber, email,
                    phoneNumber, streetName, houseNumber, floor, zipCode);
            return "register-customer";
        }
        driversLicenseNumber = customerService.normalizeDriversLicense(driversLicenseNumber);
        cprNumber = customerService.normalizeCpr(cprNumber);
        phoneNumber = customerService.normalizePhoneNumber(phoneNumber);
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

        Customer existingCustomer = customerService.getCustomerByCustomerId(customerId);
        if (existingCustomer == null) {
            return employeeService.redirectByRole(employee);
        }

        if (!customerService.isValidPhoneNumber(phoneNumber)) {
            model.addAttribute("error", "Ugyldigt telefonnummer");

            addEditFormDataToCustomerEditModel(model, customerId,
                    firstName, lastName, email, phoneNumber,
                    cprNumber, streetName, houseNumber, floor, zipCode, existingCustomer);
            return "edit-customer";
        }

        if (!customerService.isValidCpr(cprNumber)) {
            model.addAttribute("error", "Ugyldigt CPR-nummer");
            addEditFormDataToCustomerEditModel(model, customerId,
                    firstName, lastName, email, phoneNumber,
                    cprNumber, streetName, houseNumber, floor, zipCode, existingCustomer);
            return "edit-customer";
        }


        if (!zipCodeService.zipcodeExists(zipCode)) {
            model.addAttribute("error", "Postnummer ikke fundet");
            addEditFormDataToCustomerEditModel(model, customerId,
                    firstName, lastName, email, phoneNumber,
                    cprNumber, streetName, houseNumber, floor, zipCode, existingCustomer);
            return "edit-customer";
        }


        phoneNumber = customerService.normalizePhoneNumber(phoneNumber);
        cprNumber = customerService.normalizeCpr(cprNumber);
        Customer customer = new Customer(customerId, firstName, lastName, existingCustomer.getDriversLicenseNumber(), cprNumber, email, phoneNumber);
        CustomerAddress customerAddress = new CustomerAddress(customerId, zipCode, streetName, houseNumber, floor);


        String error = customerService.updateCustomer(customer, customerAddress);
        if (error != null) {
            model.addAttribute("error", error);
            addEditFormDataToCustomerEditModel(model, customerId,
                    firstName, lastName, email, phoneNumber,
                    cprNumber, streetName, houseNumber, floor, zipCode, existingCustomer);
            return "edit-customer";
        }

        return employeeService.redirectByRole(employee);
    }

    private void addFormDataToCustomerModel(Model model,
                                            String firstName, String lastName,
                                            String driversLicenseNumber, String cprNumber,
                                            String email, String phoneNumber,
                                            String streetName, String houseNumber,
                                            String floor, String zipCode) {
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("driversLicenseNumber", driversLicenseNumber);
        model.addAttribute("cprNumber", cprNumber);
        model.addAttribute("email", email);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("streetName", streetName);
        model.addAttribute("houseNumber", houseNumber);
        model.addAttribute("floor", floor);
        model.addAttribute("zipCode", zipCode);
    }

    private void addEditFormDataToCustomerEditModel(Model model, int customerId,
                                                    String firstName, String lastName,
                                                    String email, String phoneNumber, String cprNumber,
                                                    String streetName, String houseNumber,
                                                    String floor, String zipCode, Customer existing) {
        Customer customer = new Customer(customerId, firstName,
                lastName, existing.getDriversLicenseNumber(),
                cprNumber, email, phoneNumber);

        CustomerAddress address = new CustomerAddress(customerId, zipCode, streetName, houseNumber, floor);

        model.addAttribute("selectedCustomer", customer);
        model.addAttribute("selectedCustomerAddress", address);
    }
}