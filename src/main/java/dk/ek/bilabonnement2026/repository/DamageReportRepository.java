package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.DamageReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DamageReportRepository {

    @Autowired
    DataSource dataSource;

    //Daniel
    public void saveDamageReport(DamageReport damageReport) {
        String sql = "INSERT INTO damage_report (rental_contract_id, employee_id, created_at, total_price, description) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, damageReport.getRentalContractId());
            statement.setInt(2, damageReport.getEmployeeId());
            statement.setDate(3, Date.valueOf(damageReport.getCreatedAt()));
            statement.setDouble(4, damageReport.getTotalPrice());
            statement.setString(5, damageReport.getDescription());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Skades rapport kunne ikke gemmes i databasen", e);
        }
    }

    //Rune + Daniel
    public DamageReport findDamageReportByRentalContractId(int rentalContractId) {
        String sql = "SELECT * FROM damage_report WHERE rental_contract_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, rentalContractId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new DamageReport(
                            resultSet.getInt("damage_report_id"),
                            resultSet.getInt("rental_contract_id"),
                            resultSet.getInt("employee_id"),
                            resultSet.getDate("created_at").toLocalDate(),
                            resultSet.getDouble("total_price"),
                            resultSet.getString("description")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}