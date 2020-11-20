package ru.pavlytskaya.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.pavlytskaya.exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private final DataSource dataSours;

    public AccountDao() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("postgres");

        dataSours = new HikariDataSource(config);
    }

    public List<AccountModel> listOfAccount(long userID) {
        List<AccountModel> accountModel = new ArrayList<>();
        try (Connection conn = dataSours.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "select * from account where user_id = ?");
            ps.setLong(1, userID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AccountModel am = new AccountModel();
                am.setId(rs.getLong("id"));
                am.setNameAccount(rs.getString("name_account"));
                am.setBalance(rs.getDouble("balance"));
                am.setCurrency(rs.getString("currency"));
                am.setUserID(rs.getLong("user_id"));
                accountModel.add(am);
            }
        } catch (
                SQLException e) {
            throw new CustomException(e);
        }
        return accountModel;
    }


    public List<AccountModel> creatAccount(String nameAccount, double balance, String currency, long userID) {
        List<AccountModel> accountModel = new ArrayList<>();
        try (Connection conn = dataSours.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT into account (name_account, balance, currency, user_id) values (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nameAccount);
            ps.setDouble(2, balance);
            ps.setString(3, currency);
            ps.setLong(4, userID);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                AccountModel am = new AccountModel();
                am.setId(rs.getLong(1));
                am.setNameAccount(nameAccount);
                am.setBalance(balance);
                am.setCurrency(currency);
                am.setUserID(userID);
                accountModel.add(am);

                return accountModel;
            } else {
                throw new CustomException("Невозможно добавить счет");
            }

        } catch (SQLException e) {
            throw new CustomException(e);
        }

    }

    public int delete(long id) {
        try (Connection conn = dataSours.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE from account where id = ?");
            ps.setLong(1, id);

            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new CustomException(e);
        }

    }
}

