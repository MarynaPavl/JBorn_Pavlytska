package ru.pavlytskaya.entity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pavlytskaya.exception.CustomException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class TransactionInformationDao {
    @PersistenceContext
    private EntityManager em;


    @Transactional
    public TransactionInformationModel insert(Long accountFrom, Long accountTo, BigDecimal sum, LocalDate data, Set<Long> typeId) {
        TransactionInformationModel informationModel = new TransactionInformationModel();
        AccountModel aFrom = em.find(AccountModel.class, accountFrom);
        if (accountFrom > 0) {
            if (sum.compareTo(aFrom.getBalance()) > 0) {
                throw new CustomException("Transaction amount exceeds balance");
            }
            aFrom.setBalance(aFrom.getBalance().subtract(sum));
        }
        AccountModel aTo = em.find(AccountModel.class, accountTo);
        if (accountTo > 0) {
            aTo.setBalance(aTo.getBalance().add(sum));
        }
        if (accountFrom == 0 & accountTo == 0) {
            throw new CustomException("Transaction fields are filled incorrectly");
        }
        informationModel.setAccountFrom(aFrom);
        informationModel.setAccountTo(aTo);
        informationModel.setSum(sum);
        informationModel.setData(data);
        Iterator<Long> it = typeId.iterator();
        Set<TypeTransactionModel> set = new HashSet<>();
        while (it.hasNext()) {
            Long id = it.next();
            TypeTransactionModel typeTransactionModel = em.find(TypeTransactionModel.class, id);
            set.add(typeTransactionModel);
        }
        informationModel.setTypes(set);

        em.persist(informationModel);

        return informationModel;
    }

    @Transactional
    public void delete(long id) {
        TransactionInformationModel transaction = em.find(TransactionInformationModel.class, id);
        Set<TypeTransactionModel> types = transaction.getTypes();
        types.clear();
        em.remove(transaction);

    }

    @Transactional
    public List<TransactionInformationModel> informationModelList(long assignmentId, LocalDate fromDate, LocalDate toData) {

        return em.createNamedQuery(
                "Transaction.InformationList", TransactionInformationModel.class)
                .setParameter("id", assignmentId)
                .setParameter("fromData", fromDate)
                .setParameter("toData", toData)
                .getResultList();
    }

}
