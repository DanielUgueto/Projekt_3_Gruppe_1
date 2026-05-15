package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.Customer;
import dk.ek.bilabonnement2026.model.CustomerAddress;
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
public class CustomerRepository {

    @Autowired
    DataSource dataSource;


    public void createCustomer(Customer customer) {
        String sql = "INSERT INTO customer (first_name, last_name, drivers_license_number, cpr_number, email, phone_number, is_active) VALUES(?,?,?,?,?,?,?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getDriversLicenseNumber());
            statement.setString(4, customer.getCprNumber());
            statement.setString(5, customer.getEmail());
            statement.setString(6, customer.getPhoneNumber());
            statement.setBoolean(7, true);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Kunden kunne ikke gemmes i databasen", e);
        }

    }

    public Customer findCustomerByCustomerEmail(String email) {

        String sql = "SELECT * FROM customer WHERE email = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Customer customer = new Customer(

                        resultSet.getInt("customer_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("drivers_license_number"),
                        resultSet.getString("cpr_number"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getBoolean("is_active")
                );

                return customer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer findCustomerByCustomerId(int customerId) {
        Customer customer = null;
        String sql = "SELECT * FROM customer WHERE customer_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {

            statement.setInt(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    customer = new Customer(

                            resultSet.getInt("customer_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("drivers_license_number"),
                            resultSet.getString("cpr_number"),
                            resultSet.getString("email"),
                            resultSet.getString("phone_number"),
                            resultSet.getBoolean("is_active")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE is_active = true";


        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt("customer_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("drivers_license_number"),
                        resultSet.getString("cpr_number"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getBoolean("is_active"));
                customerList.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    public void updateCustomer(Customer customer){
        String sql = """
                UPDATE customer
                SET first_name = ?, last_name = ?, cpr_number = ?, email = ?, phone_number = ?
                WHERE customer_id = ?
                """;

        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(6,customer.getCustomerId());

            statement.setString(1,customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getCprNumber());
            statement.setString(4, customer.getEmail());
            statement.setString(5, customer.getPhoneNumber());

            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException("Kunden kunne ikke opdateres i databasen", e);
        }
    }

    public void setCustomerStatusInactive(int customerId) {
        String sql = "UPDATE customer SET is_active = false WHERE customer_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Kundens status kunne ikke opdateres i databasen", e);
        }
    }

    public Customer findCustomerByCustomerCprNumber(String cprNumber) {
        Customer customer = null;
        String sql = "SELECT * FROM customer WHERE cpr_number = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {

            statement.setString(1, cprNumber);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    customer = new Customer(

                            resultSet.getInt("customer_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("drivers_license_number"),
                            resultSet.getString("cpr_number"),
                            resultSet.getString("email"),
                            resultSet.getString("phone_number"),
                            resultSet.getBoolean("is_active")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public Customer findCustomerByCustomerPhoneNumber(String phoneNumber) {
        Customer customer = null;
        String sql = "SELECT * FROM customer WHERE phone_number = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {

            statement.setString(1, phoneNumber);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    customer = new Customer(
                            resultSet.getInt("customer_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("drivers_license_number"),
                            resultSet.getString("cpr_number"),
                            resultSet.getString("email"),
                            resultSet.getString("phone_number"),
                            resultSet.getBoolean("is_active")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public List<Customer> findCustomerByName(String name) {
        String sql = "SELECT c.*, ca.zip_code, ca.street_name, ca.house_number, ca.floor " +
                "FROM customer c " +
                "LEFT JOIN customer_address ca ON c.customer_id = ca.customer_id " +
                "WHERE LOWER(CONCAT(c.first_name, ' ', c.last_name)) LIKE LOWER(?)";
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + name + "%");

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {                                   //validering for hvis kunde har en adresse
                    CustomerAddress address = rs.getString("zip_code") != null ? new CustomerAddress(
                            rs.getInt("customer_id"),
                            rs.getString("zip_code"),
                            rs.getString("street_name"),
                            rs.getString("house_number"),
                            rs.getString("floor"))
                            : null;

                    Customer customer = new Customer(
                            rs.getInt("customer_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("drivers_license_number"),
                            rs.getString("cpr_number"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getBoolean("is_active"),
                            address
                    );
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
}