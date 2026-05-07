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
            e.printStackTrace();
        }
    }

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

    public DamageReport findDamageReportByDamageReportId(int damageReportId) {
        String sql = "SELECT * FROM damage_report WHERE damage_report_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, damageReportId);

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

    public List<DamageReport> findAllDamageReports() {
        List<DamageReport> reports = new ArrayList<>();
        String sql = "SELECT * FROM damage_report";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                reports.add(new DamageReport(
                        resultSet.getInt("damage_report_id"),
                        resultSet.getInt("rental_contract_id"),
                        resultSet.getInt("employee_id"),
                        resultSet.getDate("created_at").toLocalDate(),
                        resultSet.getDouble("total_price"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public void updateTotalPrice(int damageReportId, double newTotalPrice) {
        String sql = "UPDATE damage_report SET total_price = ? WHERE damage_report_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, newTotalPrice);
            statement.setInt(2, damageReportId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}