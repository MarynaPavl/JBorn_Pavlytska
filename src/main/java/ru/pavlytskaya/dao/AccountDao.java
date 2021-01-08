package ru.pavlytskaya.dao;

import org.springframework.stereotype.Service;
import ru.pavlytskaya.exception.CustomException;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountDao {
    private final DataSource dataSource;

    public AccountDao(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    public List<AccountModel> listOfAccount(long userID) {
        List<AccountModel> accountModel = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "select * from account where user_id = ?");
            ps.setLong(1, userID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AccountModel am = new AccountModel();
                am.setId(rs.getLong("id"));
                am.setNameAccount(rs.getString("name_account"));
                am.setBalance(rs.getBigDecimal("balance"));
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


    public List<AccountModel> creatAccount(String nameAccount, BigDecimal balance, String currency, long userID) {
        List<AccountModel> accountModel = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement prs = conn.prepareStatement("select *from account where name_account = ? and user_id = ?");
            prs.setString(1, nameAccount);
            prs.setLong(2, userID);
            ResultSet rst = prs.executeQuery();
            if (rst.next()) {
                throw new CustomException("Unable to add account. An account with the same name may already exist.");
            } else {

                PreparedStatement ps = conn.prepareStatement(
                        "INSERT into account (name_account, balance, currency, user_id) values (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, nameAccount);
                ps.setBigDecimal(2, balance);
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

                }
            }

        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return accountModel;
    }

    public int delete(long id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE from account where id = ?");
            ps.setLong(1, id);

            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new CustomException(e);
        }

    }
}

