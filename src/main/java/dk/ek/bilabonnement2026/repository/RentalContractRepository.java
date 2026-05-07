package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.RentalContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class RentalContractRepository {

    @Autowired
    DataSource dataSource;

    public void saveRentalContract(RentalContract rentalContract) {
        String sql = "INSERT INTO rental_contract (employee_id, customer_id, car_id, start_date, end_date, pickup_location, status, subscription_type)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, rentalContract.getEmployeeId());
            statement.setInt(2, rentalContract.getCustomerId());
            statement.setInt(3, rentalContract.getCarId());
            statement.setDate(4, Date.valueOf(rentalContract.getStartDate()));
            statement.setDate(5, Date.valueOf(rentalContract.getEndDate()));
            statement.setString(6, rentalContract.getPickupLocation());
            statement.setString(7, rentalContract.getStatus());
            statement.setString(8, rentalContract.getSubscriptionType());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RentalContract findRentalContractByCarId(int carId) {
        RentalContract rentalContract = null;
        String sql = "SELECT * FROM rental_contract WHERE car_id = ? AND status = 'Aktiv'";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, carId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    rentalContract = new RentalContract(resultSet.getInt("rental_contract_id"),
                            resultSet.getInt("employee_id"),
                            resultSet.getInt("customer_id"),
                            resultSet.getInt("car_id"),
                            resultSet.getDate("start_date").toLocalDate(),
                            resultSet.getDate("end_date").toLocalDate(),
                            resultSet.getString("pickup_location"),
                            resultSet.getString("status"),
                            resultSet.getString("subscription_type"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentalContract;
    }

    public void updateRentalContractStatus(int rentalContractId, String newStatus) {
        String sql = "UPDATE rental_contract SET status = ? WHERE rental_contract_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newStatus);
            statement.setInt(2, rentalContractId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
