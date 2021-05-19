package ru.pavlytskaya.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pavlytskaya.exception.CustomException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;

@Service
public class TransactionInformationDao {
    @PersistenceContext
    private EntityManager em;


    @Transactional
    public TransactionInformationModel insert(Long accountFrom, Long accountTo, BigDecimal sum, LocalDate data, Long typeId) {
        TransactionInformationModel informationModel = new TransactionInformationModel();
        AccountModel aFrom = em.find(AccountModel.class, accountFrom);
        if (accountFrom > 0){
           if(sum.compareTo(aFrom.getBalance()) > 0){
               throw new CustomException("Transaction amount exceeds balance");
           }
           aFrom.setBalance(aFrom.getBalance().subtract(sum));
        }
        AccountModel aTo = em.find(AccountModel.class, accountTo);
        if(accountTo > 0){
            aTo.setBalance(aTo.getBalance().add(sum));
        }
        if(accountFrom == 0 & accountTo == 0){
            throw new CustomException("Transaction fields are filled incorrectly");
        }
        informationModel.setAccountFrom(aFrom);
        informationModel.setAccountTo(aTo);
        informationModel.setSum(sum);
        informationModel.setData(data);
        informationModel.setTypes(asList(em.find(TypeTransactionModel.class, typeId)));

        em.persist(informationModel);

        return informationModel;
    }

    @Transactional
    public int delete(long id) {
        TransactionInformationModel informationModel = em.find(TransactionInformationModel.class, id);
        em.remove(informationModel);
        return 1;
    }

   @Transactional
    public List<TransactionInformationModel> informationModelList(long assignmentId, LocalDate fromDate, LocalDate toData) {

        return em.createQuery
                ("select t from TransactionInformationModel t join t.types a where a.id=:id and t.data>:fromData and t.data <:toData", TransactionInformationModel.class)
                .setParameter("id", assignmentId)
                .setParameter("fromData", fromDate)
                .setParameter("toData", toData)
                .getResultList();
    }

}
