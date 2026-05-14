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

    @GetMapping("/employee/dashboard")
    public String showEmployeeDashboard(@RequestParam(required = false) Integer employeeId,
                                        @RequestParam(required = false) String status,
                                        HttpSession session, Model model){

        Employee employee = (Employee) session.getAttribute("employee");
        if(employee == null){
            return "redirect:/";
        }
        List<Employee> employeeList;
        if(status == null || status.isBlank()){
             employeeList = employeeService.getAllEmployees();
        } else{
            employeeList = employeeService.getAllEmployeesByStatus(status);
        }

        if(employeeId != null){
            Employee selectedEmployee = null;
            for(Employee e : employeeList){
                if(e.getEmployeeId() == employeeId){
                    selectedEmployee = e;
                    break;
                }
            }
            model.addAttribute("selectedEmployee", selectedEmployee);
        }

        model.addAttribute("employee",employee);
        model.addAttribute("employeeList",employeeList);

        return"employee-dashboard";
    }

    @PostMapping("/employee/delete")
    public String deleteEmployee(@RequestParam("employeeId") int employeeId,
                                 HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee == null){
            return "redirect:/";
        }
        String message = employeeService.changeEmployeeStatus(employeeId, employee.getEmployeeId());
        if(message != null){
            model.addAttribute("error",message);
        }
        model.addAttribute("success","Medarbejderens status er sat til inaktiv");
        return "redirect:/employee/dashboard";
    }
}
