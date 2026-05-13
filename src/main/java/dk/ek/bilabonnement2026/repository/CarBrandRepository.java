package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.CarBrand;
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
public class CarBrandRepository {

    @Autowired
    DataSource dataSource;

    public List<CarBrand> getAllBrands(){
        String sql = "Select * FROM car_brand";

        List<CarBrand> list = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                CarBrand cb = new CarBrand(
                        rs.getInt("car_brand_id"),
                        rs.getString("brand_name"));

                list.add(cb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
