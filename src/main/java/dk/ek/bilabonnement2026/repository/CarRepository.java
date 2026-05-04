package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
