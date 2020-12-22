package ru.pavlytskaya.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.pavlytskaya.exception.CustomException;

import java.util.List;

import static org.junit.Assert.*;

public class TypeDaoTest {
     TypeDao subj;
    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl","jdbc:h2:mem:test_mem" );
        System.setProperty("jdbcUser","sa");
        System.setProperty("jdbcPassword","");
        System.setProperty("liquibaseFile","liquibase_user_dao_test.xml");

        subj = DaoFactory.getTypeDao();
    }

    @After
    public void after(){
        DaoFactory.dataSource = null;
    }

    @Test
    public void typeInformation_Found() {
        List<TypeTransactionModel> typeTransactionModels = subj.typeInformation();

        assertNotNull(typeTransactionModels);
        assertEquals("food", typeTransactionModels.get(0).getAssignment());

    }

    @Test(expected = CustomException.class)
    public void creatType_NotCreat() {
        subj.creatType("food");

    }

    @Test
    public void creatType_ok() {
        TypeTransactionModel typeTransactionModel = subj.creatType("travel");
        assertEquals(2L, typeTransactionModel.getId());
        assertEquals("travel", typeTransactionModel.getAssignment());
    }

}