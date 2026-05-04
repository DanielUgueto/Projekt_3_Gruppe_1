package dk.ek.bilabonnement2026.service;

import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public void addEmployeeToDatabase(Employee employee){
        if (employeeRepository.doesEmailExist(employee.getWorkEmail())){ // checks if user already exists
            return;
        }

        employeeRepository.addEmployeeToDatabase(employee);
    }
}
