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

    public boolean zipcodeExists(String zipcode) {
        String sql = "SELECT 1 FROM zip_code WHERE zip_code = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, zipcode);
            ResultSet rs = statement.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
