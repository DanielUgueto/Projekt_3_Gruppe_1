package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.model.RentalContract;
import dk.ek.bilabonnement2026.service.DamageReportService;
import dk.ek.bilabonnement2026.service.RentalContractService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class DamageReportController {

    @Autowired
    DamageReportService damageReportService;
    @Autowired
    RentalContractService rentalContractService;

    @GetMapping("/damage-reports/create/{rentalContractId}")
    public String showCreateDamageReportForm(@PathVariable int rentalContractId, HttpSession session, Model model) {

        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        RentalContract rentalContract = rentalContractService.getRentalContractByContractId(rentalContractId);
        if (rentalContract == null) {
            return "redirect:/dashboard";
        }

        model.addAttribute("rentalContract", rentalContract);
        model.addAttribute("employeeId", employee.getEmployeeId());
        model.addAttribute("createdAt", LocalDate.now());
        return "create-damage-report";
    }

    @PostMapping("/damage-reports/create/{rentalContractId}")
    public String createDamageReport(@PathVariable int rentalContractId,
                                     @RequestParam("employeeId") int employeeId,
                                     @RequestParam("totalPrice") double totalPrice,
                                     @RequestParam("description") String description,
                                     HttpSession session,
                                     Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        try {
            damageReportService.createDamageReport(rentalContractId, employeeId, totalPrice, description);
            return "redirect:/";

        } catch (IllegalArgumentException e) {
            RentalContract rentalContract = rentalContractService.getRentalContractByContractId(rentalContractId);
            model.addAttribute("rentalContract", rentalContract);
            model.addAttribute("employeeId", employeeId);
            model.addAttribute("createdAt", LocalDate.now());
            model.addAttribute("fejl", e.getMessage());

            return "create-damage-report";
        }
    }
}