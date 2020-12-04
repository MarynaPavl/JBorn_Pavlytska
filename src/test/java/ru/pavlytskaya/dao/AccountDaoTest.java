package ru.pavlytskaya.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccountDaoTest {
    AccountDao subj;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl","jdbc:h2:mem:test_mem" + UUID.randomUUID().toString());
        System.setProperty("jdbcUser","sa");
        System.setProperty("jdbcPassword","");
        System.setProperty("liquibaseFile","liquibase_user_dao_test.xml");

        subj = DaoFactory.getAccountDao();
    }

    @After
    public void after(){
        DaoFactory.dataSource = null;
    }

    @Test
    public void listOfAccount_found() {
        List<AccountModel> list = subj.listOfAccount(1);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals("main", list.get(0).getNameAccount());
        assertEquals(Double.doubleToLongBits(1000000), Double.doubleToLongBits(list.get(0).getBalance()));
        assertEquals("$", list.get(0).getCurrency());
        assertEquals(1, list.get(0).getUserID());

    }

    @Test
    public void listOfAccount_notFound() {
        List<AccountModel> list = subj.listOfAccount(2);

        assertEquals(0, list.size());
    }

    @Test
    public void creatAccount() {
        List<AccountModel> accountModelList = subj.creatAccount("save", 3000000, "$", 1);
        assertEquals(2, accountModelList.get(0).getId());
        assertEquals("save", accountModelList.get(0).getNameAccount());
        assertEquals(Double.doubleToLongBits(3000000),Double.doubleToLongBits(accountModelList.get(0).getBalance()));
        assertEquals("$", accountModelList.get(0).getCurrency());
        assertEquals(1, accountModelList.get(0).getUserID());

    }

    @Test
    public void creatAccount_notCreat() {
         subj.creatAccount("main", 3000000, "$", 1);
    }

    @Test
    public void delete() {
    }
}