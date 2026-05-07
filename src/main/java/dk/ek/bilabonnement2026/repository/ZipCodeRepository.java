package dk.ek.bilabonnement2026.repository;

import dk.ek.bilabonnement2026.model.ZipCode;
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
public class ZipCodeRepository {

    @Autowired
    DataSource dataSource;

    public List<ZipCode> getAll() {
        List<ZipCode> zipCodeList = new ArrayList<>();
        String sql = "SELECT * FROM zip_code";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ZipCode zipCode = new ZipCode(resultSet.getString("zip_code"),
                        resultSet.getString("city"),
                        resultSet.getString("country"));
                zipCodeList.add(zipCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zipCodeList;
    }
}
