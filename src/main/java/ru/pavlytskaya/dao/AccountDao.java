package ru.pavlytskaya.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<AccountModel> listOfAccount(long userID) {
        return em.createNamedQuery("Account.listAccount", AccountModel.class)
                .setParameter("userID", userID)
                .getResultList();
    }

    @Transactional
    public AccountModel creatAccount(String nameAccount, BigDecimal balance, String currency, long userID) {

        UserModel user = em.find(UserModel.class, userID);
        AccountModel accountModel = new AccountModel();
        accountModel.setNameAccount(nameAccount);
        accountModel.setBalance(balance);
        accountModel.setCurrency(currency);
        accountModel.setUser(user);


        em.persist(accountModel);

        return accountModel;
    }

    @Transactional
    public void delete(long id) {
        AccountModel accountModel = em.find(AccountModel.class, id);

        List<TransactionInformationModel> from = accountModel.getTransactionsFrom();
        List<TransactionInformationModel> to = accountModel.getTransactionsTo();
        if (from.size() > 0) {
            for (TransactionInformationModel informationFrom : from) {
                if (informationFrom.getAccountTo() == null) {
                    em.remove(informationFrom);
                } else {
                    informationFrom.setAccountFrom(null);
                }
            }

        }
        if (to.size() > 0) {
            for (TransactionInformationModel informationTo : to) {
                if (informationTo.getAccountFrom() == null) {
                    em.remove(informationTo);
                } else {
                    informationTo.setAccountTo(null);
                }
            }

        }
        accountModel.setUser(null);

        em.remove(accountModel);
    }
}