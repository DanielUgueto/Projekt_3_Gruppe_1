package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.Car;
import dk.ek.bilabonnement2026.model.CarOverview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CarRepository {

    @Autowired
    DataSource dataSource;
/*
Gælder alle metoder i denne klasse.
Vi bruger JDBC via DataSource + Connection + PreparedStatement for at beskytte mod SQL injections

Try-with-resources sikrer at både Connection, Statement og ResultSet lukkes
ved exceptions.
 */

    //Rune
    public void saveCar(Car car) {
        String sql = "INSERT INTO car (car_model_id, vin_number, license_plate, colour, status, monthly_price, registration_date)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, car.getCarModelId());
            statement.setString(2, car.getVinNumber());
            statement.setString(3, car.getLicensePlate());
            statement.setString(4, car.getColour());
            statement.setString(5, car.getStatus());
            statement.setDouble(6, car.getMonthlyPrice());
            statement.setDate(7, Date.valueOf(car.getRegistrationDate()));

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Bilen kunne ikke gemmes i databasen", e);
        }

    }

    //Rune
    public Car findCarByVinNumber(String vinNumber) {
        Car car = null;
        String sql = "SELECT * FROM car WHERE vin_number = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, vinNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    car = new Car(resultSet.getInt("car_id"),
                            resultSet.getInt("car_model_id"),
                            resultSet.getString("vin_number"),
                            resultSet.getString("license_plate"),
                            resultSet.getDouble("monthly_price"),
                            resultSet.getString("status"),
                            resultSet.getString("colour"),
                            resultSet.getDate("registration_date").toLocalDate());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    //Rune
    public Car findCarByCarNumber(int carNumber) {
        Car car = null;
        String sql = "SELECT * FROM car WHERE car_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, carNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    car = new Car(resultSet.getInt("car_id"),
                            resultSet.getInt("car_model_id"),
                            resultSet.getString("vin_number"),
                            resultSet.getString("license_plate"),
                            resultSet.getDouble("monthly_price"),
                            resultSet.getString("status"),
                            resultSet.getString("colour"),
                            resultSet.getDate("registration_date").toLocalDate());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    //Rune
    public void updateCarStatus(int carId, String status) {
        String sql = "UPDATE car SET status = ? WHERE car_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, carId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Bilens status kunne ikke opdateres i databasen", e);
        }
    }

    //Rune
    public List<Car> findCarsByStatus(String status) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM car WHERE status = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cars.add(new Car(resultSet.getInt("car_id"),
                            resultSet.getInt("car_model_id"),
                            resultSet.getString("vin_number"),
                            resultSet.getString("license_plate"),
                            resultSet.getDouble("monthly_price"),
                            resultSet.getString("status"),
                            resultSet.getString("colour"),
                            resultSet.getDate("registration_date").toLocalDate()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    // Nico
    public List<CarOverview> findAllCarsWithDetails() {
        String sql = "SELECT * FROM car " +
                "JOIN car_model ON car.car_model_id = car_model.car_model_id " +
                "JOIN car_brand ON car_model.car_brand_id = car_brand.car_brand_id";

        List<CarOverview> list = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                CarOverview carOverview = new CarOverview(
                        rs.getString("brand_name"),
                        rs.getInt("car_id"),
                        rs.getString("colour"),
                        rs.getString("equipment_level"),
                        rs.getString("license_plate"),
                        rs.getString("model_name"),
                        rs.getDouble("monthly_price"),
                        rs.getString("shift_gear_type"),
                        rs.getString("status"),
                        rs.getString("vin_number"),
                        rs.getDate("registration_date").toLocalDate(),
                        rs.getString("fuel_type"));

                list.add(carOverview);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Nico
    public List<CarOverview> findAllCarsWithDetailsByStatus(String status) {
        String sql = "SELECT * FROM car c " +
                "JOIN car_model cm ON c.car_model_id = cm.car_model_id " +
                "JOIN car_brand cb ON cm.car_brand_id = cb.car_brand_id " +
                "WHERE c.status = ?";

        List<CarOverview> list = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                CarOverview carOverview = new CarOverview(
                        rs.getString("brand_name"),
                        rs.getInt("car_id"),
                        rs.getString("colour"),
                        rs.getString("equipment_level"),
                        rs.getString("license_plate"),
                        rs.getString("model_name"),
                        rs.getDouble("monthly_price"),
                        rs.getString("shift_gear_type"),
                        rs.getString("status"),
                        rs.getString("vin_number"),
                        rs.getDate("registration_date").toLocalDate(),
                        rs.getString("fuel_type"));

                list.add(carOverview);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Nico
    public CarOverview findCarOverviewByCarId(int carId) {
        String sql = "SELECT * FROM car c " +
                "JOIN car_model cm ON c.car_model_id = cm.car_model_id " +
                "JOIN car_brand cb ON cm.car_brand_id = cb.car_brand_id " +
                "WHERE c.car_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, carId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new CarOverview(
                        rs.getString("brand_name"),
                        rs.getInt("car_id"),
                        rs.getString("colour"),
                        rs.getString("equipment_level"),
                        rs.getString("license_plate"),
                        rs.getString("model_name"),
                        rs.getDouble("monthly_price"),
                        rs.getString("shift_gear_type"),
                        rs.getString("status"),
                        rs.getString("vin_number"),
                        rs.getDate("registration_date").toLocalDate(),
                        rs.getString("fuel_type"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Nico
    public void updateCar(Car car) {
        String sql = "UPDATE car " +
                "SET car_model_id = ?, vin_number = ?, license_plate = ?, monthly_price = ?, status = ?, colour = ?, registration_date = ? " +
                "WHERE car_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, car.getCarModelId());
            statement.setString(2, car.getVinNumber());
            statement.setString(3, car.getLicensePlate());
            statement.setDouble(4, car.getMonthlyPrice());
            statement.setString(5, car.getStatus());
            statement.setString(6, car.getColour());
            statement.setDate(7, Date.valueOf(car.getRegistrationDate()));
            statement.setInt(8, car.getCarId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Bilen kunne ikke opdateres i databasen", e);
        }
    }

    // Nico
    public Car findCarByLicensePlate(String licensePlate) {
        String sql = "SELECT * FROM car WHERE license_plate = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, licensePlate);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Car(resultSet.getInt("car_id"),
                            resultSet.getInt("car_model_id"),
                            resultSet.getString("vin_number"),
                            resultSet.getString("license_plate"),
                            resultSet.getDouble("monthly_price"),
                            resultSet.getString("status"),
                            resultSet.getString("colour"),
                            resultSet.getDate("registration_date").toLocalDate());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Nico
    public int returnCarAmountByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM car WHERE status = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
