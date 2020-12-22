package ru.pavlytskaya.dao;

import ru.pavlytskaya.exception.CustomException;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionInformationDao {
    private final DataSource dataSource;

    public TransactionInformationDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TransactionInformationModel insert(Integer accountFrom, Integer accountTo, BigDecimal sum, LocalDate data) {
        TransactionInformationModel informationModel = null;
        AccountModel am = new AccountModel();
        BigDecimal newSumTo;
        BigDecimal newSumFrom;
        ResultSet rs = null;
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement("select *from account where id = ?");
            if (accountFrom != 0 & accountTo != 0 | accountFrom != 0 & accountTo == 0) {
                ps.setInt(1, accountFrom);
                rs = ps.executeQuery();
                if (rs.next()) {
                    am.setBalance(rs.getBigDecimal("balance"));
                }
                newSumFrom = am.getBalance().subtract(sum);
                if (newSumFrom.compareTo(BigDecimal.valueOf(0)) < 0) {
                    conn.rollback();
                    throw new CustomException("The balance on this account is exceeded");
                }
                PreparedStatement psNew = conn.prepareStatement("update account set balance = ? where id = ?");
                psNew.setBigDecimal(1, newSumFrom);
                psNew.setInt(2, accountFrom);
                psNew.executeUpdate();
            }
            if (accountFrom != 0 & accountTo != 0 | accountFrom == 0 & accountTo != 0) {
                ps.setInt(1, accountTo);
                rs = ps.executeQuery();
                if (rs.next()) {
                    am.setBalance(rs.getBigDecimal("balance"));
                }
                newSumTo = am.getBalance().add(sum);
                PreparedStatement psNew = conn.prepareStatement("update account set balance = ? where id = ?");
                psNew.setBigDecimal(1, newSumTo);
                psNew.setInt(2, accountTo);
                psNew.executeUpdate();
            }
            if (accountFrom == 0 & accountTo != 0) {
                PreparedStatement ps1 = conn.prepareStatement(
                        "INSERT into transaction (id_account_to, sum, time) values (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps1.setInt(1, accountTo);
                ps1.setBigDecimal(2, sum);
                ps1.setDate(3, Date.valueOf(data));
                ps1.executeUpdate();
                rs = ps1.getGeneratedKeys();

            }
            if (accountFrom != 0 & accountTo == 0) {
                PreparedStatement ps1 = conn.prepareStatement(
                        "INSERT into transaction (id_account_from,  sum, time ) values (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps1.setInt(1, accountFrom);
                ps1.setBigDecimal(2, sum);
                ps1.setDate(3, Date.valueOf(data));
                ps1.executeUpdate();
                rs = ps1.getGeneratedKeys();

            }
            if (accountFrom != 0 & accountTo != 0) {
                PreparedStatement ps1 = conn.prepareStatement(
                        "INSERT into transaction (id_account_from, id_account_to, sum, time ) values (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps1.setInt(1, accountFrom);
                ps1.setInt(2, accountTo);
                ps1.setBigDecimal(3, sum);
                ps1.setDate(4, Date.valueOf(data));
                ps1.executeUpdate();
                rs = ps1.getGeneratedKeys();
            }
            assert rs != null;
            if (rs.next()) {
                informationModel = new TransactionInformationModel();
                informationModel.setId(rs.getLong(1));
                informationModel.setAccountFrom(accountFrom);
                informationModel.setAccountTo(accountTo);
                informationModel.setSum(sum);
                informationModel.setData(data);
            }
            conn.commit();

            return informationModel;

        } catch (SQLException e) {
            throw new CustomException(e);
        }

    }
    public int delete(long id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE from transaction where id = ?");
            ps.setLong(1, id);

            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new CustomException("Key (id) = (15) is referenced in the \"transaction_to_category\" table. " +
                    "First, do the delete on the \"transaction_to_category\" table.");
        }

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
                    trModel.setSum(rs1.getBigDecimal("sum"));
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
