package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.CustomerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CustomerAddressRepository {

    @Autowired
    DataSource dataSource;

    // US06-T06: saveCustomerAddress()
    public void saveCustomerAddress(CustomerAddress customerAddress) {
        String sql = "INSERT INTO customer_address (customer_id, zip_code, street_name, house_number, floor) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerAddress.getCustomerId());
            statement.setString(2, customerAddress.getZipCode());
            statement.setString(3, customerAddress.getStreetName());
            statement.setString(4, customerAddress.getHouseNumber());
            statement.setString(5, customerAddress.getFloor());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CustomerAddress findCustomerAddressByCustomerId(int customerId) {
        String sql = "SELECT * FROM customer_address WHERE customer_id = ?";
        CustomerAddress customerAddress = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    customerAddress = new CustomerAddress(resultSet.getInt("customer_address_id"),
                            resultSet.getInt("customer_id"),
                            resultSet.getString("zip_code"),
                            resultSet.getString("street_name"),
                            resultSet.getString("house_number"),
                            resultSet.getString("floor"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerAddress;
    }
}