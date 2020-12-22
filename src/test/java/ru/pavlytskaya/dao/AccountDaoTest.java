package ru.pavlytskaya.dao;

import org.junit.Before;
import org.junit.Test;
import ru.pavlytskaya.exception.CustomException;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class AccountDaoTest {
    AccountDao subj;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl","jdbc:h2:mem:test_mem" );
        System.setProperty("jdbcUser","sa");
        System.setProperty("jdbcPassword","");
        System.setProperty("liquibaseFile","liquibase_user_dao_test.xml");

        subj = DaoFactory.getAccountDao();
    }


    @Test
    public void listOfAccount_found() {
        List<AccountModel> list = subj.listOfAccount(1);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals("main", list.get(0).getNameAccount());
        assertEquals(1000000, list.get(0).getBalance().intValue());
        assertEquals("$", list.get(0).getCurrency());
        assertEquals(1, list.get(0).getUserID());
    }

    @Test
    public void listOfAccount_notFound() {
        List<AccountModel> list = subj.listOfAccount(2);

        assertEquals(0, list.size());
    }

    @Test
    public void creatAccount_ok() {
        //есть более изящный вариант, чем везде в тесте обернуть в valueOf?
        List<AccountModel> accountModelList = subj.creatAccount("save", BigDecimal.valueOf(3000000), "$", 1);
        assertEquals(2, accountModelList.get(0).getId());
        assertEquals("save", accountModelList.get(0).getNameAccount());
        assertEquals(3000000, accountModelList.get(0).getBalance().intValue());
        assertEquals("$", accountModelList.get(0).getCurrency());
        assertEquals(1, accountModelList.get(0).getUserID());

    }

    @Test(expected = CustomException.class)
    public void creatAccount_notCreat() {
        subj.creatAccount("main", BigDecimal.valueOf(3000000), "$", 1);
    }

    @Test
    public void delete() {
        int userModel = subj.delete(0);
        assertEquals(0, userModel);
    }

}