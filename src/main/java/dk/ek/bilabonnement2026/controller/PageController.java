package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Employee;
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

    @GetMapping("/")
    public String loginPage(HttpSession session){
        Employee employee = (Employee) session.getAttribute("employee");

        if (employee != null){
            return employeeService.redirectByRole(employee);
        }

        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session){

        Employee employee = employeeService.login(email, password);

        if (employee == null){
            return "login";
        }

        session.setAttribute("employee", employee);

        return employeeService.redirectByRole(employee);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/dashboard/forretningsudvikling")
    public String showBusinessInfo(HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        double monthlyRevenue = rentalContractService.calculateMonthlyRevenue();
        model.addAttribute("monthlyRevenue", monthlyRevenue);

        return "business-dashboard";
    }

    @GetMapping("/dashboard/dataregistrering")
    public String showDataDashboard(HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        return "dataregistration-dashboard";
    }
}
