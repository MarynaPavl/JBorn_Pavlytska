package ru.pavlytskaya.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionInformationDao {
    @PersistenceContext
    private EntityManager em;

    private final AccountDao accountDao;

    public TransactionInformationDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    @Transactional
    public TransactionInformationModel insert(Long accountFrom, Long accountTo, BigDecimal sum, LocalDate data) {
        TransactionInformationModel informationModel = new TransactionInformationModel();
        AccountModel aFrom = em.find(AccountModel.class, accountFrom);
        AccountModel aTo = em.find(AccountModel.class, accountTo);
        informationModel.setAccountFrom(aFrom);
        informationModel.setAccountTo(aTo);
        informationModel.setSum(sum);
        informationModel.setData(data);

        em.persist(informationModel);

//        AccountModel am = new AccountModel();
//        BigDecimal newSumTo;
//        BigDecimal newSumFrom;
//        ResultSet rs = null;
//        try (Connection conn = dataSource.getConnection()) {
//            conn.setAutoCommit(false);
//            PreparedStatement ps = conn.prepareStatement("select *from account where id = ?");
//            if (accountFrom != 0 & accountTo != 0 | accountFrom != 0 & accountTo == 0) {
//                ps.setInt(1, accountFrom);
//                rs = ps.executeQuery();
//                if (rs.next()) {
//                    am.setBalance(rs.getBigDecimal("balance"));
//                }
//                newSumFrom = am.getBalance().subtract(sum);
//                if (newSumFrom.compareTo(BigDecimal.valueOf(0)) < 0) {
//                    conn.rollback();
//                    throw new CustomException("The balance on this account is exceeded");
//                }
//                PreparedStatement psNew = conn.prepareStatement("update account set balance = ? where id = ?");
//                psNew.setBigDecimal(1, newSumFrom);
//                psNew.setInt(2, accountFrom);
//                psNew.executeUpdate();
//            }
//            if (accountFrom != 0 & accountTo != 0 | accountFrom == 0 & accountTo != 0) {
//                ps.setInt(1, accountTo);
//                rs = ps.executeQuery();
//                if (rs.next()) {
//                    am.setBalance(rs.getBigDecimal("balance"));
//                }
//                newSumTo = am.getBalance().add(sum);
//                PreparedStatement psNew = conn.prepareStatement("update account set balance = ? where id = ?");
//                psNew.setBigDecimal(1, newSumTo);
//                psNew.setInt(2, accountTo);
//                psNew.executeUpdate();
//            }
//            if (accountFrom == 0 & accountTo != 0) {
//                PreparedStatement ps1 = conn.prepareStatement(
//                        "INSERT into transaction (id_account_to, sum, time) values (?, ?, ?)",
//                        Statement.RETURN_GENERATED_KEYS);
//                ps1.setInt(1, accountTo);
//                ps1.setBigDecimal(2, sum);
//                ps1.setDate(3, Date.valueOf(data));
//                ps1.executeUpdate();
//                rs = ps1.getGeneratedKeys();
//
//            }
//            if (accountFrom != 0 & accountTo == 0) {
//                PreparedStatement ps1 = conn.prepareStatement(
//                        "INSERT into transaction (id_account_from,  sum, time ) values (?, ?, ?)",
//                        Statement.RETURN_GENERATED_KEYS);
//                ps1.setInt(1, accountFrom);
//                ps1.setBigDecimal(2, sum);
//                ps1.setDate(3, Date.valueOf(data));
//                ps1.executeUpdate();
//                rs = ps1.getGeneratedKeys();
//
//            }
//            if (accountFrom != 0 & accountTo != 0) {
//                PreparedStatement ps1 = conn.prepareStatement(
//                        "INSERT into transaction (id_account_from, id_account_to, sum, time ) values (?, ?, ?, ?)",
//                        Statement.RETURN_GENERATED_KEYS);
//                ps1.setInt(1, accountFrom);
//                ps1.setInt(2, accountTo);
//                ps1.setBigDecimal(3, sum);
//                ps1.setDate(4, Date.valueOf(data));
//                ps1.executeUpdate();
//                rs = ps1.getGeneratedKeys();
//            }
//            assert rs != null;
//            if (rs.next()) {
//                informationModel = new TransactionInformationModel();
//                informationModel.setId(rs.getLong(1));
//                informationModel.setAccountFrom(accountFrom);
//                informationModel.setAccountTo(accountTo);
//                informationModel.setSum(sum);
//                informationModel.setData(data);
//            }
//            conn.commit();
//
//            return informationModel;
//
//        } catch (SQLException e) {
//            throw new CustomException(e);
//        }
      return  informationModel;
    }

    public int delete(long id) {

            return 1;


    }


    public List<TransactionInformationModel> informationModelList(long assignmentId, LocalDate fromDate, LocalDate toData) {

        return em.createQuery
                ("select t from TransactionInformationModel t join t.types a where a.id=:id and t.data>:fromData and t.data <:toData",TransactionInformationModel.class)
                .setParameter("id", assignmentId)
                .setParameter("fromData", fromDate)
                .setParameter("toData", toData)
                .getResultList();
    }

}
