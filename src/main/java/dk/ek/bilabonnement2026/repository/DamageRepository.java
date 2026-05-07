package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.Damage;
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
public class DamageRepository {

    @Autowired
    DataSource dataSource;

    public void save(Damage damage) {
        String sql = "INSERT INTO damage (damage_report_id, damage_category_id) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, damage.getDamageReportId());
            statement.setInt(2, damage.getDamageCategoryId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Damage> findByDamageReportId(int damageReportId) {
        List<Damage> dameges = new ArrayList<>();
        String sql = "SELECT * FROM damage WHERE damage_report_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, damageReportId);

            while (resultSet.next()) {
                dameges.add(new Damage(resultSet.getInt("damage_id"),
                        resultSet.getInt("damage_report_id"),
                        resultSet.getInt("damage_category_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dameges;
    }
}
