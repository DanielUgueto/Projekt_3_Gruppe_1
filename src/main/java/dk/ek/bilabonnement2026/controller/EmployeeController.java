package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    // Nico
    @GetMapping("/addEmployee")
    public String addEmployee(HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");

        if (employee == null) {
            return "redirect:/";
        }

        return "add-employee";
    }

    // Nico
    @PostMapping("/addEmployee")
    public String addEmployeeToDatabase(@RequestParam("firstName") String firstName,
                                        @RequestParam("lastName") String lastName,
                                        @RequestParam("password") String password,
                                        @RequestParam("workEmail") String workEmail,
                                        @RequestParam("role") String role, HttpSession session) {

        Employee loggedInEmployee = (Employee) session.getAttribute("employee");
        if (loggedInEmployee == null) {
            return "redirect:/";
        }
        if (!loggedInEmployee.getRole().equalsIgnoreCase("dataregistrering")) { // if there was an admin role they should also be granted access
            return employeeService.redirectByRole(loggedInEmployee); // makes sure only Data people can create accounts
        }

        Employee employee = new Employee(firstName, lastName, password, workEmail, role);
        boolean emailExists = employeeService.addEmployeeToDatabase(employee);

        if (!emailExists) {
            return "add-employee";
        }

        return employeeService.redirectByRole(loggedInEmployee);
    }

    //Rune
    @GetMapping("/employee/dashboard")
    public String showEmployeeDashboard(@RequestParam(required = false) Integer employeeId,
                                        @RequestParam(required = false) String status,
                                        HttpSession session, Model model) {

        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }
        List<Employee> employeeList;

        if (status == null || status.isBlank()) {
            employeeList = employeeService.getAllEmployees();
        } else {
            employeeList = employeeService.getAllEmployeesByStatus(status);
        }

        if (employeeId != null) {
            Employee selectedEmployee = employeeService.getEmployeeById(employeeId);
            model.addAttribute("selectedEmployee", selectedEmployee);
        }

        model.addAttribute("employee", employee);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("status", status);

        return "employee-dashboard";
    }

    //Rune
    @PostMapping("/employee/delete")
    public String deleteEmployee(@RequestParam("employeeId") int employeeId,
                                 @RequestParam(value = "status", required = false) String status,
                                 HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }
        String message = employeeService.changeEmployeeStatus(employeeId, employee.getEmployeeId());
        if (message != null) {
            model.addAttribute("error", message);
        }
        model.addAttribute("success", "Medarbejderens status er sat til inaktiv");

        return "redirect:/employee/dashboard?status=" + (status != null ? status : "");
    }

    //Daniel
    @PostMapping("/employee/reactivate")
    public String reactivateEmployee(@RequestParam("employeeId") int employeeId,
                                     @RequestParam(value = "status", required = false) String status,
                                     HttpSession session, Model model) {

        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }
        String error = employeeService.setEmployeeStatusActive(employeeId);
        if (error != null) {
            List<Employee> list = employeeService.getAllEmployees();
            Employee deletedEmployee = null;
            for (Employee e : list) {
                if (e.getEmployeeId() == employeeId) {
                    deletedEmployee = e;
                    break;
                }
            }
            model.addAttribute("employeeList", list);
            model.addAttribute("selectedEmployee", deletedEmployee);
            model.addAttribute("reactivationError", error);
            return "employee-dashboard";
        }
        return "redirect:/employee/dashboard?status=" + (status != null ? status : "");
    }
}
