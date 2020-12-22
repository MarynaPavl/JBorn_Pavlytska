package ru.pavlytskaya.dao;

import ru.pavlytskaya.exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;

public class TransactionToCategoryDao {
    private final DataSource dataSource;

    public TransactionToCategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TransactionToCategoryModel insert(Long transactionId, Long typeId){
        TransactionToCategoryModel model = null;
        try(Connection conn = dataSource.getConnection()){
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT into transaction_to_category (transaction_id, category_id) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1,transactionId);
            ps.setLong(2, typeId);
            ps.executeUpdate();

            PreparedStatement pst = conn.prepareStatement(
                    "SELECT *from transaction_to_category where transaction_id = ? and category_id = ?");
            pst.setLong(1, transactionId);
            pst.setLong(2, typeId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                model = new TransactionToCategoryModel();
                model.setIdTransaction(rs.getLong("transaction_id"));
                model.setIdCategory(rs.getLong("category_id"));
            }
            return model;
        } catch (SQLException e) {
            throw new CustomException(e);
        }

    }

}
