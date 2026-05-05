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
                        resultSet.getString("shift_gear_type")
                );
                carModelsList.add(carModel);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return carModelsList;
    }
}
