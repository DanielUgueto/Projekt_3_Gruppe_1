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

    public void saveDamageCategory(DamageCategory category){
        String sql = "INSERT INTO damage_category (name, standard_price, description) VALUES (?,?,?)";

        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, category.getName());
            statement.setDouble(2, category.getStandardPrice());
            statement.setString(3, category.getDescription());

            statement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException("Skadekategorien kunne ikke gemmes i databasen", e);
        }
    }

    public void updateDamageCategory(DamageCategory category){
        String sql = """
                UPDATE damage_category
                SET name = ?, standard_price = ?, description = ?
                WHERE damage_category_id = ?
                """;

        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, category.getName());
            statement.setDouble(2, category.getStandardPrice());
            statement.setString(3, category.getDescription());
            statement.setInt(4, category.getDamageCategoryId());

            statement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException("Skadekategorien kunne ikke opdateres i databasen", e);
        }
    }

    public void updateDamageCategoryIsActive(int damageCategoryId, boolean isActive){
        String sql = """
                UPDATE damage_category 
                SET is_active = ? 
                WHERE damage_category_id = ?
                """;

        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setBoolean(1,isActive);
            statement.setInt(2,damageCategoryId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw  new RuntimeException("Skadekategoriens status kunne ikke opdateres i databasen", e);
        }
    }

    public List<DamageCategory> getAllDamageCategories() {
        List<DamageCategory> categoryList = new ArrayList<>();
        String sql = "SELECT * FROM damage_category WHERE is_active = true";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                categoryList.add(new DamageCategory(resultSet.getInt("damage_category_id"),
                                resultSet.getString("name"),
                                resultSet.getDouble("standard_price"),
                                resultSet.getString("description"),
                        resultSet.getBoolean("is_active")));
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
                            resultSet.getString("description"),
                            resultSet.getBoolean("is_active"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DamageCategory findDamageCategoryByName(String name){
        String sql = "SELECT * FROM damage_category WHERE name = ?";

        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,name);

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    return new DamageCategory(resultSet.getInt("damage_category_id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("standard_price"),
                    resultSet.getString("description"),
                    resultSet.getBoolean("is_active")
                    );
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
