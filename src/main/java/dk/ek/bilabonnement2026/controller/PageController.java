package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.service.CarService;
import dk.ek.bilabonnement2026.service.EmployeeService;
import dk.ek.bilabonnement2026.service.RentalContractService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PageController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    RentalContractService rentalContractService;

    @Autowired
    CarService carService;


    // Nico
    @GetMapping("/")
    public String loginPage(HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");

        if (employee != null) {
            return employeeService.redirectByRole(employee);
        }

        return "login";
    }

    // Nico
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session, Model model) {

        Employee employee = employeeService.login(email, password);

        if (employee == null) {
            model.addAttribute("error", "Du er ikke logget ind.");
            return "login";
        }
        if (!employee.getIsActive()) {
            model.addAttribute("error", "Din bruger er inaktiv, kontakt en administrator.");
            return "login";
        }

        session.setAttribute("employee", employee);

        return employeeService.redirectByRole(employee);
    }

    // Nico
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // Nico
    @GetMapping("/dashboard/forretningsudvikling")
    public String showBusinessInfo(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        double monthlyRevenue = rentalContractService.calculateMonthlyRevenue();
        int rentalContracts = rentalContractService.returnAmountOfContractsByStatus("Aktiv");
        int availableCars = carService.returnCarAmountByStatus("Ledig");
        int rentedCars = carService.returnCarAmountByStatus("Udlejet");
        double projectedYearlyRevenue = rentalContractService.projectedYearlyRevenue();

        model.addAttribute("monthlyRevenue", formatRevenue(monthlyRevenue));
        model.addAttribute("activeRentalContracts", rentalContracts);
        model.addAttribute("availableCars", availableCars);
        model.addAttribute("rentedCars", rentedCars);
        model.addAttribute("projectedYearlyRevenue", formatRevenue(projectedYearlyRevenue));

        return "business-dashboard";
    }

    // Nico
    @GetMapping("/dashboard/dataregistrering")
    public String showDataDashboard(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }
        return "dataregistration-dashboard";
    }

    //Daniel
    //hjælpe metode til formaterin
    public String formatRevenue(double amount) {

        if (amount % 1 == 0) {
            return String.format("%,.0f", amount).replace(',', '.');
        } else {
            return String.format("%,.2f", amount).replace(',', '.');
        }
    }

}
