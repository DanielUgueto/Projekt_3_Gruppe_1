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
            return "redirect:/dashboard/dataregistrering";
        }
        if (employee.getRole().equalsIgnoreCase("skade-udbedring")){
            return "redirect:/dashboard/damage";
        }
        if (employee.getRole().equalsIgnoreCase("forretningsudvikler")){
            return "redirect:/dashboard/forretningsudvikling";
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

    public List<Employee> getAllEmployees(){
     return employeeRepository.findAllEmployees();
    }

    public List<Employee> getAllEmployeesByStatus(String status){
        if(status == null){
            return null;
        }
        boolean employeeStatus;
        if(status.equalsIgnoreCase("aktiv")){
            employeeStatus = true;
        } else if (status.equalsIgnoreCase("inaktiv")){
            employeeStatus = false;
        }else {
            return null;
        }

        return employeeRepository.findAllEmployeesByStatus(employeeStatus);
    }

    public String changeEmployeeStatus(int employeeId, int sessionEmployeeId){
        if(employeeId == sessionEmployeeId){
            return "Ikke muligt at slette dig selv";
        }
        boolean is_active = true;
        Employee givenEmployee = employeeRepository.findEmployeeByEmployeeId(employeeId);
        if(givenEmployee == null){
            return "Medarbejderen kunne ikke findes i systemet";
        }

        if(givenEmployee.getIs_active()){
            is_active = false;
        }
       return employeeRepository.updateEmployeeIsActive(is_active,givenEmployee.getEmployeeId());
    }
}
