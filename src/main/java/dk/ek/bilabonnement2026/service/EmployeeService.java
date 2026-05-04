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
}
