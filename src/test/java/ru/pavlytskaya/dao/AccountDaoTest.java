package ru.pavlytskaya.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.pavlytskaya.exception.CustomException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class AccountDaoTest {
    AccountDao subj;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl","jdbc:h2:mem:test_mem" + UUID.randomUUID().toString());
        System.setProperty("jdbcUser","sa");
        System.setProperty("jdbcPassword","");
        System.setProperty("liquibaseFile","liquibase_user_dao_test.xml");
        ApplicationContext context = new AnnotationConfigApplicationContext("ru.pavlytskaya");
        subj = context.getBean(AccountDao.class);
    }


    @Test
    public void listOfAccount_found() {
        List<AccountModel> list = subj.listOfAccount(1);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals("main", list.get(0).getNameAccount());
        assertEquals(1000000, list.get(0).getBalance().intValue());
        assertEquals("$", list.get(0).getCurrency());
        assertEquals(1, list.get(0).getUserModel().getId());
    }

    @Test
    public void listOfAccount_notFound() {
        List<AccountModel> list = subj.listOfAccount(2);

        assertEquals(0, list.size());
    }

    @Test
    public void creatAccount_ok() {
        //есть более изящный вариант, чем везде в тесте обернуть в valueOf?
        AccountModel accountModel = subj.creatAccount("save", BigDecimal.valueOf(3000000), "$", 1);
        assertEquals(2, accountModel.getId());
        assertEquals("save", accountModel.getNameAccount());
        assertEquals(3000000, accountModel.getBalance().intValue());
        assertEquals("$", accountModel.getCurrency());
        assertEquals(1, accountModel.getUserModel().getId());

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