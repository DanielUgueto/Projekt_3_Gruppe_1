package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

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

    @GetMapping("/addEmployee")
    public String addEmployee(HttpSession session){
        Employee employee = (Employee) session.getAttribute("employee");

        if (employee == null){
            return "redirect:/";
        }

        return "addEmployee";
    }

    @PostMapping("/addEmployee")
    public String addEmployeeToDatabase(@RequestParam("firstName") String firstName,
                                        @RequestParam("lastName") String lastName,
                                        @RequestParam("password") String password,
                                        @RequestParam("workEmail") String workEmail,
                                        @RequestParam("role") String role, HttpSession session) {

        Employee loggedInEmployee = (Employee)  session.getAttribute("employee");
        if (loggedInEmployee == null){
            return "redirect:/";
        }
        if (!loggedInEmployee.getRole().equalsIgnoreCase("dataregistrering")){ // if there was an admin role they should also be granted access
            return employeeService.redirectByRole(loggedInEmployee); // makes sure only Data people can create accounts
        }

        Employee employee = new Employee(firstName, lastName, password, workEmail, role);
        boolean emailExists = employeeService.addEmployeeToDatabase(employee);

        if (!emailExists){
            return "addEmployee";
        }

        return employeeService.redirectByRole(loggedInEmployee);
    }
}
