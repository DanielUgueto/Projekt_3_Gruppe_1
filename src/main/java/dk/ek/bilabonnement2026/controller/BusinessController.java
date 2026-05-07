package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.service.RentalContractService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BusinessController {

    @Autowired
    RentalContractService rentalContractService;

    @GetMapping("/business-info")
    public String showBusinessInfo(HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        double monthlyRevenue = rentalContractService.calculateMonthlyRevenue();
        model.addAttribute("monthlyRevenue", monthlyRevenue);

        return "business-dashboard";
    }
}
