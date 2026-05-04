package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CustomerRepository {

    @Autowired
    DataSource dataSource;

    public void createCustomer(Customer customer) {
        String sql = "INSERT INTO customer (first_name, last_name, drivers_license_number, cpr_number, email, phone_number) VALUES(?,?,?,?,?,?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getDriversLicenseNumber());
            statement.setString(4, customer.getCprNumber());
            statement.setString(5, customer.getEmail());
            statement.setInt(6, customer.getPhoneNumber());


            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Customer findCustomerByCustomerEmail(String email) {

        String sql = "SELECT * FROM customer WHERE email = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Customer customer = new Customer();

                customer.setId(resultSet.getInt("customer_id"));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setDriversLicenseNumber(resultSet.getString("drivers_license_number"));
                customer.setCprNumber(resultSet.getString("cpr_number"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhoneNumber(resultSet.getInt("phone_number"));

                return customer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}