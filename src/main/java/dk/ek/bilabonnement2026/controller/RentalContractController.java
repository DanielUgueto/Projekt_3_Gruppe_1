package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Car;
import dk.ek.bilabonnement2026.model.Customer;
import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.model.RentalContract;
import dk.ek.bilabonnement2026.repository.CarRepository;
import dk.ek.bilabonnement2026.repository.CustomerRepository;
import dk.ek.bilabonnement2026.service.CarService;
import dk.ek.bilabonnement2026.service.CustomerService;
import dk.ek.bilabonnement2026.service.EmployeeService;
import dk.ek.bilabonnement2026.service.RentalContractService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RentalContractController {

    @Autowired
    RentalContractService rentalContractService;
    @Autowired
    CarService carService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/rental-contracts/create")
    public String showCreateRentalContractForm(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        List<Car> cars = carService.findCarsByStatus("Ledig");
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("cars", cars);
        model.addAttribute("customers", customers);
        model.addAttribute("employeeId", employee.getEmployeeId());
        return "createRentalContract";
    }

    @PostMapping("/rental-contracts/create")
    public String createRentalContract(@RequestParam("employeeId") int employeeId,
                                       @RequestParam("customerId") int customerId,
                                       @RequestParam("carId") int carId,
                                       @RequestParam("startDate") String startDate,
                                       @RequestParam("endDate") String endDate,
                                       @RequestParam("pickupLocation") String pickupLocation,
                                       @RequestParam("subscriptionType") String subscriptionType,
                                       HttpSession session,
                                       Model model) {

        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        RentalContract rentalContract = new RentalContract(
                employeeId, customerId, carId,
                LocalDate.parse(startDate), LocalDate.parse(endDate),
                pickupLocation, "Aktiv", subscriptionType
        );

        try {
            rentalContractService.createRentalContract(rentalContract);
            return employeeService.redirectByRole(employee);
        } catch (IllegalArgumentException e) {
            List<Car> cars = carService.findCarsByStatus("Ledig");
            List<Customer> customers = customerService.getAllCustomers();
            model.addAttribute("cars", cars);
            model.addAttribute("customers", customers);
            model.addAttribute("employeeId", employee.getEmployeeId());
            model.addAttribute("fejl", e.getMessage());
            return "createRentalContract";
        }
    }

    @GetMapping("/rental-contracts/edit")
    public String showEditContractForm(@RequestParam("rentalContractId") int rentalContractId,
                                       HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }
        if (!employee.getRole().equalsIgnoreCase("dataregistrering")) {
            return "redirect:/";
        }

        RentalContract rentalContract = rentalContractService.getRentalContractByContractId(rentalContractId);

        model.addAttribute("rentalContract", rentalContract);

        return "edit-rentalContract";
    }

    @PostMapping("/rental-contracts")
    public String editRentalContract(@RequestParam("rentalContractId") int rentalContractId,
                                     @RequestParam("startDate") LocalDate startDate,
                                     @RequestParam("endDate") LocalDate endDate,
                                     @RequestParam("pickupLocation") String pickupLocation,
                                     @RequestParam("subscriptionType") String subscriptionType,
                                     HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }
        if (!employee.getRole().equalsIgnoreCase("dataregistrering")) {
            return "redirect:/";
        }

        RentalContract rentalContract = new RentalContract(rentalContractId, startDate, endDate, pickupLocation, subscriptionType);

        try {
            rentalContractService.updateRentalContract(rentalContract);
            redirectAttributes.addFlashAttribute("success", "Kontrakten blev opdateret");
            return "redirect:/rental-contracts/" + rentalContractId;
        } catch (IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("rentalContractId", rentalContractId);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            model.addAttribute("pickupLocation", pickupLocation);
            model.addAttribute("subscriptionType", subscriptionType);
            return "rental-contract/edit";
        }

    }

    @GetMapping("/rental-contracts/return")
    public String showReturnCarForm(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }
        List<Car> carList = carService.findCarsByStatus("Udlejet");

        model.addAttribute("carList", carList);
        model.addAttribute("employeeId", employee.getEmployeeId());

        return "returnCar";
    }

    @PostMapping("/rental-contracts/return")
    public String returnCar(HttpSession session, @RequestParam("carId") int carId, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }
        try {
            rentalContractService.registerReturnOfCar(carId);
            return employeeService.redirectByRole(employee);
        } catch (IllegalArgumentException e) {
            List<Car> carList = carService.findCarsByStatus("Udlejet");
            model.addAttribute("carList", carList);
            model.addAttribute("fejl", e.getMessage());
            return "returnCar";
        }

    }
}