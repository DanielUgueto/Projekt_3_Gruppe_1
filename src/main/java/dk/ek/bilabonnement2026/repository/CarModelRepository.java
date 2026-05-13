package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.CarModel;
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
public class CarModelRepository {

    @Autowired
    DataSource dataSource;

    public List<CarModel> getAllCarModels(){
        List<CarModel> carModelsList = new ArrayList<>();
        String sql = "SELECT * FROM car_model";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){

            while(resultSet.next()){
                CarModel carModel = new CarModel(resultSet.getInt("car_model_id"),
                        resultSet.getInt("car_brand_id"),
                        resultSet.getString("model_name"),
                        resultSet.getString("equipment_level"),
                        resultSet.getString("shift_gear_type"),
                        resultSet.getString("fuel_type")
                );
                carModelsList.add(carModel);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return carModelsList;
    }

    public CarModel getCarModelByCarModelId(int carModelId){
        String sql = "SELECT * FROM car_model WHERE car_model_id = ?";

        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, carModelId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
                return new CarModel(rs.getInt("car_model_id"),
                        rs.getInt("car_brand_id"),
                        rs.getString("model_name"),
                        rs.getString("equipment_level"),
                        rs.getString("shift_gear_type"),
                        rs.getString("fuel_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateCarModel(CarModel carModel){
        String sql = "UPDATE car_model " +
                "SET car_brand_id = ?, model_name = ?, equipment_level = ?, shift_gear_type = ?, fuel_type = ? " +
                "WHERE car_model_id = ?";

        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, carModel.getCarBrandId());
            statement.setString(2, carModel.getModelName());
            statement.setString(3, carModel.getEquipmentLevel());
            statement.setString(4, carModel.getShiftGearType());
            statement.setString(5, carModel.getFuelType());
            statement.setInt(6, carModel.getCarModelId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
