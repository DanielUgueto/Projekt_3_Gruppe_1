package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.DamageCategory;
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
public class DamageCategoryRepository {

    @Autowired
    DataSource dataSource;

    public List<DamageCategory> getAllDamageCategories() {
        List<DamageCategory> categoryList = new ArrayList<>();
        String sql = "SELECT * FROM damage_category";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                categoryList.add(new DamageCategory(resultSet.getInt("damage_category_id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("standard_price"),
                        resultSet.getString("description")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    public DamageCategory findById(int damageCategoryId) {
        String sql = "SELECT * FROM damage_category WHERE damage_category_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, damageCategoryId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new DamageCategory(resultSet.getInt("damage_category_id"),
                            resultSet.getString("name"),
                            resultSet.getDouble("standard_price"),
                            resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
