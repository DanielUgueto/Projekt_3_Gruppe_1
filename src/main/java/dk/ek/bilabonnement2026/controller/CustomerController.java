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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    ZipCodeService zipCodeService;
    @Autowired
    EmployeeService employeeService;


    //Rune
    @GetMapping("/customer/register")
    public String showRegisterCustomer(HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        return "register-customer";
    }

    //Rune
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
        //Tjek om mobilnummer er valid
        if (!customerService.isValidPhoneNumber(phoneNumber)) {
            model.addAttribute("error", "Ugyldigt telefonnummer");
            addFormDataToCustomerModel(model, firstName, lastName,
                    driversLicenseNumber, cprNumber, email,
                    phoneNumber, streetName, houseNumber, floor, zipCode);
            return "register-customer";
        }
        //Tjek om cpr-nummer er valid
        if (!customerService.isValidCpr(cprNumber)) {
            model.addAttribute("error", "Ugyldigt CPR-nummer");
            addFormDataToCustomerModel(model, firstName, lastName,
                    driversLicenseNumber, cprNumber, email,
                    phoneNumber, streetName, houseNumber, floor, zipCode);
            return "register-customer";
        }
        //Tjek om kørekort nummer er valid
        if (!customerService.isValidDriversLicense(driversLicenseNumber)) {
            model.addAttribute("error", "Ugyldigt Kørekort nummer");
            addFormDataToCustomerModel(model, firstName, lastName,
                    driversLicenseNumber, cprNumber, email,
                    phoneNumber, streetName, houseNumber, floor, zipCode);
            return "register-customer";
        }
        //Tjek om post nummer er valid
        if (!zipCodeService.zipcodeExists(zipCode)) {
            model.addAttribute("wrongZipcode", "Postnummer ikke fundet");
            addFormDataToCustomerModel(model, firstName, lastName,
                    driversLicenseNumber, cprNumber, email,
                    phoneNumber, streetName, houseNumber, floor, zipCode);
            return "register-customer";
        }
        //Tildeler normaliseret data til variablerne og bygger customer, customerAdress.
        driversLicenseNumber = customerService.normalizeDriversLicense(driversLicenseNumber);
        cprNumber = customerService.normalizeCpr(cprNumber);
        phoneNumber = customerService.normalizePhoneNumber(phoneNumber);
        Customer customer = new Customer(firstName, lastName, driversLicenseNumber, cprNumber, email, phoneNumber, true);
        CustomerAddress customerAddress = new CustomerAddress(0, zipCode, streetName, houseNumber, floor);
        String error = customerService.registerCustomer(customer, customerAddress);
        //Hvis strengen error ikke er null så der sket en fejl
        if (error != null) {
            model.addAttribute("error", error);
            return "register-customer";
        }

        return employeeService.redirectByRole(employee);
    }

    //Rune
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

    //Rune
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
        Customer customer = new Customer(customerId, firstName, lastName, existingCustomer.getDriversLicenseNumber(), cprNumber, email, phoneNumber, existingCustomer.getIsActive());
        CustomerAddress customerAddress = new CustomerAddress(customerId, zipCode, streetName, houseNumber, floor);


        String error = customerService.updateCustomer(customer, customerAddress);
        if (error != null) {
            model.addAttribute("error", error);
            addEditFormDataToCustomerEditModel(model, customerId,
                    firstName, lastName, email, phoneNumber,
                    cprNumber, streetName, houseNumber, floor, zipCode, existingCustomer);
            return "edit-customer";
        }

        model.addAttribute("success", "Kunden er opdateret");
        addEditFormDataToCustomerEditModel(model, customerId,
                firstName, lastName, email, phoneNumber,
                cprNumber, streetName, houseNumber, floor, zipCode, customer);
        return "edit-customer";
    }

    @PostMapping("/customer/delete")
    public String deleteCustomer(@RequestParam("customerId") int customerId,
                                 @RequestParam(value = "status", required = false) String status,
                                 HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }
        String error = customerService.setCustomerStatusInactive(customerId);
        if (error != null) {
            List<Customer> list = customerService.getAllCustomers();
            Customer selectedCustomer = null;
            for (Customer c : list) {
                if (c.getCustomerId() == customerId) {
                    selectedCustomer = c;
                    break;
                }
            }
            model.addAttribute("customerList", list);
            model.addAttribute("selectedCustomer", selectedCustomer);
            model.addAttribute("deleteError", error);
            return "customer-dashboard";
        }
        return "redirect:/customer/dashboard?status=" + (status != null ? status : "");
    }

    @PostMapping("/customer/reactivate")
    public String reactivateCustomer(@RequestParam("customerId") int customerId,
                                     @RequestParam(value = "status", required = false) String status,
                                     HttpSession session, Model model) {

        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }
        String error = customerService.setCustomerStatusActive(customerId);
        if (error != null) {
            List<Customer> list = customerService.getAllCustomers();
            Customer selectedCustomer = null;
            for (Customer c : list) {
                if (c.getCustomerId() == customerId) {
                    selectedCustomer = c;
                    break;
                }
            }
            model.addAttribute("customerList", list);
            model.addAttribute("selectedCustomer", selectedCustomer);
            model.addAttribute("reactivationError", error);
            return "customer-dashboard";
        }
        return "redirect:/customer/dashboard?status=" + (status != null ? status : "");
    }


    @GetMapping("/customer/dashboard")
    public String showCustomerDashboard(@RequestParam(required = false) String status,
                                        @RequestParam(required = false) Integer customerId,
                                        @RequestParam(required = false) String query,
                                        HttpSession session, Model model) {

        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        List<Customer> list;
        //hvis der er sendt et søge parameter med i parameter så søges der på det navn.
        if (query != null && !query.isBlank()) {
            list = customerService.searchCustomerByName(query);
            model.addAttribute("query", query);
            if (list.isEmpty()) {
                model.addAttribute("error", "Ingen kunder fundet med navnet: \"" + query + "\"");
            }
            //Hvis der søges efter aktive eller inaktive kunder og hvis ingen så sendes alle kunder
        } else if ("aktiv".equals(status)) {
            list = customerService.getAllActiveCustomers();

        } else if ("inaktiv".equals(status)) {
            list = customerService.getAllInactiveCustomers();

        } else {
            list = customerService.getAllCustomers();
        }
        //Den valgte kunde sendes med tilbage som model atrribut.
        if (customerId != null) {
            Customer selectedCustomer = null;
            for (Customer c : list) {
                if (c.getCustomerId() == customerId) {
                    selectedCustomer = c;
                    break;
                }
            }
            model.addAttribute("selectedCustomer", selectedCustomer);
        }

        model.addAttribute("status", status);
        model.addAttribute("customerList", list);

        return "customer-dashboard";
    }



    //Denne metode sætter alle model attributterne for createCustomer. Lavet for at undgå for meget redundans
    //Rune
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

    //Denne metode sætter alle model attributter for updateCustomer, med de parameter de kan opdatere.
    //Rune
    private void addEditFormDataToCustomerEditModel(Model model, int customerId,
                                                    String firstName, String lastName,
                                                    String email, String phoneNumber, String cprNumber,
                                                    String streetName, String houseNumber,
                                                    String floor, String zipCode, Customer existing) {
        Customer customer = new Customer(customerId, firstName,
                lastName, existing.getDriversLicenseNumber(),
                cprNumber, email, phoneNumber, existing.getIsActive());

        CustomerAddress address = new CustomerAddress(customerId, zipCode, streetName, houseNumber, floor);

        model.addAttribute("selectedCustomer", customer);
        model.addAttribute("selectedCustomerAddress", address);
    }

}