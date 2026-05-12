package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.CarOverview;
import dk.ek.bilabonnement2026.model.RentalContract;
import dk.ek.bilabonnement2026.model.RentalContractOverview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public RentalContract findRentalContractById(int rentalContractId) {
        RentalContract rentalContract = null;
        String sql = "SELECT * FROM rental_contract WHERE rental_contract_id = ? ";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, rentalContractId);

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

    public List<CarOverview> findReturnedCarsWithContract() {
        List<CarOverview> cars = new ArrayList<>();
        String sql = """
                SELECT c.car_id, cb.brand_name, cm.model_name, cm.equipment_level,
                       cm.shift_gear_type, cm.fuel_type, c.vin_number, c.license_plate,
                       c.monthly_price, c.status, c.colour, c.registration_date,
                       rc.rental_contract_id
                FROM rental_contract rc
                JOIN car c ON rc.car_id = c.car_id
                JOIN car_model cm ON c.car_model_id = cm.car_model_id
                JOIN car_brand cb ON cm.car_brand_id = cb.car_brand_id
                WHERE rc.status = 'Afsluttet'
                AND c.status IN ('Tilbageleveret', 'Klar til transport')
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                cars.add(new CarOverview(
                        resultSet.getInt("car_id"),
                        resultSet.getString("brand_name"),
                        resultSet.getString("model_name"),
                        resultSet.getString("equipment_level"),
                        resultSet.getString("shift_gear_type"),
                        resultSet.getString("vin_number"),
                        resultSet.getString("license_plate"),
                        resultSet.getDouble("monthly_price"),
                        resultSet.getString("status"),
                        resultSet.getString("colour"),
                        resultSet.getString("registration_date"),
                        resultSet.getString("fuel_type"),
                        resultSet.getInt("rental_contract_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
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

    public double calculateMonthlyRevenue() {
        String sql = "SELECT monthly_price FROM rental_contract JOIN car ON rental_contract.car_id = car.car_id WHERE rental_contract.status = ?";

        double monthlyRevenue = 0;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "Aktiv");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                monthlyRevenue += rs.getDouble("monthly_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monthlyRevenue;
    }

    public List<RentalContractOverview> findAllRentalContractOverviews(String statusFilter) {
        /*
        rentalContractId -> rental_contract
        customerFirstName -> customer
        customerLastName -> customer
        brandName -> car_brand
        modelName -> car_model
        licensePlate -> car
        startDate -> rental_contract
        endDate -> rental_contract
        status -> rental_contract
        monthlyPrice -> rental_contract

        rental_contract = rc
        customer = cu
        car_brand = cb
        car_model = cm
        car = c

        5 tabels total to join
*/

        List<RentalContractOverview> rentalContractOverviewList = new ArrayList<>();

        String sql = """ 
                SELECT rc.rental_contract_id, rc.start_date, rc.end_date, rc.status, rc.pickup_location,
                       cu.first_name, cu.last_name, c.license_plate, c.monthly_price, cb.brand_name, cm.model_name
                FROM rental_contract rc 
                JOIN customer cu ON rc.customer_id = cu.customer_id
                JOIN car c ON rc.car_id = c.car_id
                JOIN car_model cm ON c.car_model_id = cm.car_model_id
                JOIN car_brand cb ON cm.car_brand_id = cb.car_brand_id
                """;

        if (statusFilter != null && !statusFilter.isBlank()) {
            sql += " WHERE rc.status = ?";
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (statusFilter != null && !statusFilter.isBlank()) {
                statement.setString(1, statusFilter);
            }

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    rentalContractOverviewList.add(new RentalContractOverview(resultSet.getInt("rental_contract_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("brand_name"),
                            resultSet.getString("model_name"),
                            resultSet.getString("license_plate"),
                            resultSet.getDate("start_date").toLocalDate(),
                            resultSet.getDate("end_date").toLocalDate(),
                            resultSet.getString("status"),
                            resultSet.getString("pickup_location"),
                            resultSet.getDouble("monthly_price")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentalContractOverviewList;
    }

    public RentalContractOverview findRentalContractOverviewById(int contractId) {

        RentalContractOverview rentalContractOverview = null;
        String sql = """ 
                SELECT rc.rental_contract_id, rc.start_date, rc.end_date, rc.status, rc.pickup_location,
                       cu.first_name, cu.last_name, c.license_plate, c.monthly_price, cb.brand_name, cm.model_name
                FROM rental_contract rc 
                JOIN customer cu ON rc.customer_id = cu.customer_id
                JOIN car c ON rc.car_id = c.car_id
                JOIN car_model cm ON c.car_model_id = cm.car_model_id
                JOIN car_brand cb ON cm.car_brand_id = cb.car_brand_id
                WHERE rc.rental_contract_id = ?
                """;


        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
             ) {
            statement.setInt(1,contractId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                  rentalContractOverview = new RentalContractOverview(resultSet.getInt("rental_contract_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("brand_name"),
                            resultSet.getString("model_name"),
                            resultSet.getString("license_plate"),
                            resultSet.getDate("start_date").toLocalDate(),
                            resultSet.getDate("end_date").toLocalDate(),
                            resultSet.getString("status"),
                            resultSet.getString("pickup_location"),
                            resultSet.getDouble("monthly_price"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentalContractOverview;
    }
}
