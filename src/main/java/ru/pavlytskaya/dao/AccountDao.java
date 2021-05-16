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
        return em.createQuery("select a from AccountModel a where a.userModel.id =:userID", AccountModel.class)
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
        accountModel.setUserModel(user);


        em.persist(accountModel);

        return accountModel;
    }

    @Transactional
    public int delete(long id) {
        AccountModel accountModel = em.find(AccountModel.class, id);
        em.remove(accountModel);
        return 1;
    }

}