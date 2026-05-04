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

    @GetMapping("/addEmployee")
    public String addEmployee(HttpSession session){
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null){
            return "redirect:/login";
        }

        return "addEmployee";
    }

    @PostMapping("/addEmployee")
    public String addEmployeeToDatabase(@RequestParam("firstName") String firstName,
                                        @RequestParam("lastName") String lastName,
                                        @RequestParam("password") String password,
                                        @RequestParam("workEmail") String workEmail,
                                        @RequestParam("role") String role) {

        Employee employee = new Employee(firstName, lastName, password, workEmail, role);

        boolean emailExists = employeeService.addEmployeeToDatabase(employee);

        if (!emailExists){
            return "addEmployee";
        }

        return "dataRegistration";
    }
}
