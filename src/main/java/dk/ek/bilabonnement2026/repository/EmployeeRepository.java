package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            statement.setString(4, employee.getEmail());
            statement.setString(5, employee.getRole());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Medarbejderen kunne ikke gemmes i databasen", e);
        }
    }

    public String updateEmployeeStatus(boolean isActive, int employeeId) {
        String sql = "UPDATE employee SET is_active = ? WHERE employee_id = ?";

        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setBoolean(1,isActive);
            statement.setInt(2,employeeId);
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException("Medarbejderens status kunne ikke opdateres i databasen", e);
        }
        return null;
    }

    public Boolean doesEmailExist(String email) {
        String sql = "Select * FROM employee WHERE work_email = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
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

            if (rs.next()) {
                Employee employee = new Employee(rs.getInt("employee_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("password"),
                        rs.getString("work_email"),
                        rs.getString("role"),
                        rs.getBoolean("is_active")
                );

                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Employee findEmployeeByEmployeeId(int id) {
        String sql = "Select * FROM employee WHERE employee_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Employee employee = new Employee(rs.getInt("employee_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("work_email"),
                        rs.getString("role"),
                        rs.getBoolean("is_active")
                );

                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Employee> findAllEmployees() {
        String sql = "SELECT * FROM employee";

        List<Employee> employeeList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Employee employee = new Employee(resultSet.getInt("employee_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("work_email"),
                        resultSet.getString("role"),
                        resultSet.getBoolean("is_active"));

                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public List<Employee> findAllEmployeesByStatus(boolean status) {
        String sql = "SELECT * FROM employee WHERE is_active = ?";
        List<Employee> employeeList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setBoolean(1, status);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Employee employee = new Employee(resultSet.getInt("employee_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("work_email"),
                            resultSet.getString("role"),
                            resultSet.getBoolean("is_active"));

                    employeeList.add(employee);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }
}
