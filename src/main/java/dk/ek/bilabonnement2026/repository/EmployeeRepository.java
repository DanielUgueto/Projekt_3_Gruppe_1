package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.EmployeeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class EmployeeRepository {

    @Autowired
    DataSource dataSource;

    public void addEmployee(EmployeeModel employee){
        String sql = "INSERT INTO employee (first_name, last_name, password, work_email, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getPassword());
            statement.setString(4, employee.getWorkEmail());
            statement.setString(5, employee.getRole());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
