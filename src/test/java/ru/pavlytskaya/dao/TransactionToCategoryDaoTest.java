package ru.pavlytskaya.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.pavlytskaya.exception.CustomException;

import static org.junit.Assert.assertEquals;

public class TransactionToCategoryDaoTest {
    TransactionToCategoryDao subj;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl","jdbc:h2:mem:test_mem" );
        System.setProperty("jdbcUser","sa");
        System.setProperty("jdbcPassword","");
        System.setProperty("liquibaseFile","liquibase_user_dao_test.xml");
        ApplicationContext context = new AnnotationConfigApplicationContext("ru.pavlytskaya");
        subj = context.getBean(TransactionToCategoryDao.class);
    }


    @Test(expected = CustomException.class)
    public void insert_notCreated() {
        subj.insert(3L, 1L);

    }

    @Test
    public void insert_ok() {
        TransactionToCategoryModel insert = subj.insert(1L, 1L);
        assertEquals(1L, insert.getIdTransaction());
        assertEquals(1L, insert.getIdCategory());
    }
}