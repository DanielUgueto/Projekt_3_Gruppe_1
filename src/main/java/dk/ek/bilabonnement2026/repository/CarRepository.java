package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.Car;
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
public class CarRepository {

    @Autowired
    DataSource dataSource;
    public void saveCar(Car car){
        String sql = "INSERT INTO car (car_model_id, vin_number, license_plate, colour, status, monthly_price)"
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1,car.getCarModelId());
            statement.setString(2, car.getVinNumber());
            statement.setString(3, car.getLicensePlate());
            statement.setString(4, car.getColour());
            statement.setString(5, car.getStatus());
            statement.setDouble(6,car.getMonthlyPrice());

            statement.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public Car findCarByVinNumber(String vinNumber){
        Car car = null;
        String sql = "SELECT * FROM car WHERE vin_number = ?";

        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,vinNumber);

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    car = new Car(resultSet.getInt("car_id"),
                            resultSet.getInt("car_model_id"),
                            resultSet.getString("vin_number"),
                            resultSet.getString("license_plate"),
                            resultSet.getDouble("monthly_price"),
                            resultSet.getString("status"),
                            resultSet.getString("colour"));
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return car;
    }

    public Car findCarByCarNumber(int carNumber){
        Car car = null;
        String sql = "SELECT * FROM car WHERE car_id = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1,carNumber);

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    car = new Car(resultSet.getInt("car_id"),
                            resultSet.getInt("car_model_id"),
                            resultSet.getString("vin_number"),
                            resultSet.getString("license_plate"),
                            resultSet.getDouble("monthly_price"),
                            resultSet.getString("status"),
                            resultSet.getString("colour"));
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return car;
    }

    public void updateCarStatus(Car car) {
        String sql = "UPDATE car SET status = ? WHERE car_id = ?";

        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, car.getStatus());
            statement.setInt(2,car.getCarId());

            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Car> findCarsByStatus(String status){
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM car WHERE status = ?";

        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,status);

            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    cars.add(new Car(resultSet.getInt("car_id"),
                            resultSet.getInt("car_model_id"),
                            resultSet.getString("vin_number"),
                            resultSet.getString("license_plate"),
                            resultSet.getDouble("monthly_price"),
                            resultSet.getString("status"),
                            resultSet.getString("colour")));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cars;
    }
}
