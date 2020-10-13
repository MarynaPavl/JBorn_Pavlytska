package ru.pavlytskaya.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.pavlytskaya.exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;

public class TypeDao {
    private final DataSource dataSours;

    public TypeDao() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("postgres");

        dataSours = new HikariDataSource(config);
    }

    public TypeTransactionModel creatType(String assignment) {
        TypeTransactionModel type = new TypeTransactionModel();
        try (Connection conn = dataSours.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT into category (assignment) values (?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, assignment);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                type.setId(rs.getLong(1));
                type.setAssignment(assignment);

                return type;
            } else {
                throw new CustomException("Невозможно добавить тип");
            }

        } catch (SQLException e) {
            throw new CustomException(e);
        }

    }

    public int editType(long id, String assignment) {
        int row;
        try (Connection con = dataSours.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE  category set assignment = ? where id = ?");
            ps.setString(1, assignment);
            ps.setLong(2, id);

            row = ps.executeUpdate();


        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return row;
    }

    public int delete(long id) {
        try (Connection conn = dataSours.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE from category where id = ?");
            ps.setLong(1, id);

            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new CustomException(e);
        }

    }

}
