package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Customer;
import dk.ek.bilabonnement2026.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/customer/register")
    public String showRegisterCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "register-customer";
    }

    @PostMapping("/customer/register")
    public String registerCustomer(@ModelAttribute Customer customer, Model model) {
        String error=customerService.registerCustomer(customer);

        if (error != null){
            model.addAttribute("error",error);
            model.addAttribute("customer",customer);
        }

        return "redirect:/index";
    }
}