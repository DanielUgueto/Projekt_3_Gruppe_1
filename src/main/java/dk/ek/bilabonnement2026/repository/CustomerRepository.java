package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.Customer;
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
        String sql = "INSERT INTO customer (first_name, last_name, drivers_license_number, cpr_number, email, phone_number) VALUES(?,?,?,?,?,?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setInt(3, customer.getDriversLicenseNumber());
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
                Customer customer = new Customer(

                resultSet.getInt("customer_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getInt("drivers_license_number"),
                resultSet.getString("cpr_number"),
                resultSet.getString("email"),
                resultSet.getInt("phone_number")
                );

                return customer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setCustomerStatusInactive(int customerId) {
        String sql = "UPDATE customer SET status = 'inactive' WHERE customer_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE status != 'inactive' OR status IS NULL";


        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){

            while(resultSet.next()){
                Customer customer = new Customer(resultSet.getInt("customer_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("drivers_license_number"),
                        resultSet.getString("cpr_number"),
                        resultSet.getString("email"),
                        resultSet.getInt("phone_number"));
                customerList.add(customer);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return customerList;
    }
}