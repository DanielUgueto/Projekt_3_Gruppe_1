package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    // Nico
    public boolean addEmployeeToDatabase(Employee employee) {
        if (employeeRepository.doesEmailExist(employee.getEmail())) { // checks if user already exists
            System.out.println("User already exists in database");
            return false;
        }

        employeeRepository.addEmployeeToDatabase(employee);
        return true;
    }

    // metoden retunere en redirect string baseret på medarbejderens rolle.
    //Bruges efter login og efter succesfulde POST for at sende brugeren tilbage til
    //det dashboard der høre til dem.
    //Nico
    public String redirectByRole(Employee employee) {
        if (employee.getRole().equalsIgnoreCase("dataregistrering")) {
            return "redirect:/dashboard/dataregistrering";
        }
        if (employee.getRole().equalsIgnoreCase("skade-udbedring")) {
            return "redirect:/dashboard/damage";
        }
        if (employee.getRole().equalsIgnoreCase("forretningsudvikler")) {
            return "redirect:/dashboard/forretningsudvikling";
        }
        return "/index";
    }

    // Nico
    public Employee login(String email, String password) {
        Employee employee = employeeRepository.findEmployeeByEmail(email);

        if (employee == null) {
            System.out.println("Employee not found");
            return null;
        }

        if (!employee.getPassword().equals(password)) {
            System.out.println("Wrong password");
            return null;
        }

        return employee;
    }

    //Rune
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllEmployees();
    }

    //Rune
    public List<Employee> getAllEmployeesByStatus(String status) {
        if (status == null) {
            return null;
        }
        boolean employeeStatus;
        if (status.equalsIgnoreCase("aktiv")) {
            employeeStatus = true;
        } else if (status.equalsIgnoreCase("inaktiv")) {
            employeeStatus = false;
        } else {
            return null;
        }

        return employeeRepository.findAllEmployeesByStatus(employeeStatus);
    }

    //Rune
    public String changeEmployeeStatus(int employeeId, int sessionEmployeeId) {

        if (employeeId == sessionEmployeeId) {
            return "Ikke muligt at slette dig selv";
        }
        boolean isActive = true;

        Employee givenEmployee = employeeRepository.findEmployeeByEmployeeId(employeeId);
        if (givenEmployee == null) {
            return "Medarbejderen kunne ikke findes i systemet";
        }

        if (givenEmployee.getIsActive()) {
            isActive = false;
        }
        return employeeRepository.updateEmployeeStatus(isActive, givenEmployee.getEmployeeId());
    }

    //sætter altid aktiv for genaktivering modsat metoden ligeover der fungerer som en toggle
    public String setEmployeeStatusActive(int employeeId) {

        Employee deletedEmployee = employeeRepository.findEmployeeByEmployeeId(employeeId);
        if (deletedEmployee == null) {
            return "Medarbejder findes ikke i systemet";
        }
        if (deletedEmployee.getIsActive()) {
            return "Medarbejder er allerede aktiv i systemet";
        }

        return employeeRepository.updateEmployeeStatus(true, employeeId);
    }

    public Employee getEmployeeById(int employeeId) {
        return employeeRepository.findEmployeeByEmployeeId(employeeId);
    }

}
