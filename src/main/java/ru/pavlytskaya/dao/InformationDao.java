package ru.pavlytskaya.dao;

import ru.pavlytskaya.exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InformationDao {
    private final DataSource dataSource;

    public InformationDao(DataSource dataSource) {
       this.dataSource = dataSource;
    }

    public List<TransactionInformationModel> informationModelList(long assignmentId, LocalDate fromDate, LocalDate toData) {
        List<TransactionInformationModel> informationModels = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "select *from transaction_to_category where category_id = ?");
            ps.setLong(1, assignmentId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TransactionInformationModel trModel = new TransactionInformationModel();
                trModel.setId(rs.getLong("transaction_id"));
                PreparedStatement ps1 = conn.prepareStatement(
                        "select *from transaction where id = ? and time > ? and time < ?");
                ps1.setLong(1, trModel.getId());
                ps1.setDate(2, Date.valueOf(fromDate));
                ps1.setDate(3, Date.valueOf(toData));

                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    trModel.setId(rs1.getLong("id"));
                    trModel.setAccountFrom(rs1.getInt("id_account_from"));
                    trModel.setAccountTo(rs1.getInt("id_account_to"));
                    trModel.setSum(rs1.getDouble("sum"));
                    trModel.setData(rs1.getDate("time").toLocalDate());
                    informationModels.add(trModel);

                }
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return informationModels;
    }
}
