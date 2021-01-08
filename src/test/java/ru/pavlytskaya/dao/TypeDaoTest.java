package ru.pavlytskaya.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.pavlytskaya.exception.CustomException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TypeDaoTest {
     TypeDao subj;
    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl","jdbc:h2:mem:test_mem" );
        System.setProperty("jdbcUser","sa");
        System.setProperty("jdbcPassword","");
        System.setProperty("liquibaseFile","liquibase_user_dao_test.xml");
        ApplicationContext context = new AnnotationConfigApplicationContext("ru.pavlytskaya");
        subj = context.getBean(TypeDao.class);
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