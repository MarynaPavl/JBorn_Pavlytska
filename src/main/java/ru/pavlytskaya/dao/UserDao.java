package ru.pavlytskaya.dao;

import ru.pavlytskaya.exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
   this.dataSource = dataSource;
    }

    public UserModel findByEmailAndHash(String email, String hash) {
        UserModel userModel = null;
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select *from service_users where email_address = ? and password = ?");
            ps.setString(1, email);
            ps.setString(2, hash);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userModel = new UserModel();
                userModel.setId(rs.getLong("id"));
                userModel.setFirstName(rs.getString("first_name"));
                userModel.setLastName(rs.getString("last_name"));
                userModel.setEmail(rs.getString("email_address"));
                userModel.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }

        return userModel;
    }

    public UserModel insert(String firstName, String lastName, String email, String hash) {
        UserModel userModel = null;
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement prs = conn.prepareStatement("select *from service_users where email_address = ?");

            prs.setString(1,email);

            ResultSet rst = prs.executeQuery();
            if(rst.next()){
                throw new CustomException("Unable to generate id or user already exists ");

            }else {
                PreparedStatement ps = conn.prepareStatement(" INSERT into service_users (first_name, last_name, email_address, password)" +
                          " values (?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                ps.setString(4, hash);

                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    userModel = new UserModel();
                    userModel.setId(rs.getLong(1));
                    userModel.setFirstName(firstName);
                    userModel.setLastName(lastName);
                    userModel.setEmail(email);
                    userModel.setPassword(hash);
                }
                return userModel;
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }

    }

}
