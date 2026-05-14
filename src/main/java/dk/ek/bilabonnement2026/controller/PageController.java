package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Customer;
import dk.ek.bilabonnement2026.model.Employee;
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

import java.util.List;

@Controller
public class PageController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    RentalContractService rentalContractService;
    @Autowired
    CustomerService customerService;

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
                        HttpSession session, Model model){

        Employee employee = employeeService.login(email, password);

        if (employee == null){
            model.addAttribute("error","Du er ikke logget ind.");
            return "login";
        }
        if(!employee.getIs_active()){
            model.addAttribute("error","Din bruger er inaktiv, kontakt en administrator.");
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
