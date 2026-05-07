package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public boolean addEmployeeToDatabase(Employee employee){
        if (employeeRepository.doesEmailExist(employee.getWorkEmail())){ // checks if user already exists
            System.out.println("User already exists in database");
            return false;
        }

        employeeRepository.addEmployeeToDatabase(employee);
        return true;
    }

    public String redirectByRole(Employee employee){
        if (employee.getRole().equalsIgnoreCase("dataregistrering")){
            return "redirect:/dataregistration";
        }
        if (employee.getRole().equalsIgnoreCase("skade-udbedring")){
            return "redirect:/skade-udbedring";
        }
        if (employee.getRole().equalsIgnoreCase("forretningsudvikler")){
            return "redirect:/business-dashboard";
        }
        return "/index";
    }

    public Employee login(String email, String password){
        Employee employee = employeeRepository.findEmployeeByEmail(email);

        if (employee == null){
            System.out.println("Employee not found");
            return null;
        }

        if (!employee.getPassword().equals(password)){
            System.out.println("Wrong password");
            return null;
        }

        return employee;
    }
}
