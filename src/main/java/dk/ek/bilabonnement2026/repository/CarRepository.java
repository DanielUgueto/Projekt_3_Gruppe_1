package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.Car;
import dk.ek.bilabonnement2026.model.CarOverview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public ArrayList<CarOverview> findAllCarsWithDetails(){
        String sql = "SELECT * FROM car " +
                "JOIN car_model ON car.car_model_id = car_model.car_model_id " +
                "JOIN car_brand ON car_model.car_brand_id = car_brand.car_brand_id";

        ArrayList<CarOverview> list = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
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
                        rs.getString("vin_number"));

                list.add(carOverview);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
