package ru.pavlytskaya.dao;

import org.springframework.stereotype.Service;
import ru.pavlytskaya.exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TypeDao {
    private final DataSource dataSource;

    public TypeDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<TypeTransactionModel> typeInformation() {
        List<TypeTransactionModel> typeTransactionModels = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "select *from category");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TypeTransactionModel type = new TypeTransactionModel();
                type.setId(rs.getLong("id"));
                type.setAssignment(rs.getString("assignment"));
                typeTransactionModels.add(type);
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return typeTransactionModels;
    }

    public TypeTransactionModel creatType(String assignment) {

        TypeTransactionModel type = new TypeTransactionModel();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement prs = conn.prepareStatement("select *from category where assignment = ?");
            prs.setString(1, assignment);
            ResultSet rst = prs.executeQuery();
            if (rst.next()) {
                throw new CustomException("Unable to add type");
            } else {
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT into category (assignment) values (?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, assignment);

                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    type.setId(rs.getLong(1));
                    type.setAssignment(assignment);


                }
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return type;
    }

}
