package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class EmployeeRepository {

    @Autowired
    DataSource dataSource;

    public void addEmployeeToDatabase(Employee employee) {
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

    public Boolean doesEmailExist(String email) {
        String sql = "Select * FROM employee WHERE work_email = ?";

        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Employee findEmployeeByEmail(String email) {
        String sql = "Select * FROM employee WHERE work_email = ?";

        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
                Employee employee = new Employee(rs.getInt("employee_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("password"),
                        rs.getString("work_email"),
                        rs.getString("role")
                );

                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Email not exist");
        return null;
    }
}
