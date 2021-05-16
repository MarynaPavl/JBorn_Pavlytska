package ru.pavlytskaya.dao;


import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.pavlytskaya.exception.CustomException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class TransactionInformationDaoTest {
    TransactionInformationDao subj;
    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl","jdbc:h2:mem:test_mem" + UUID.randomUUID().toString());
        System.setProperty("jdbcUser","sa");
        System.setProperty("jdbcPassword","");
        System.setProperty("liquibaseFile","liquibase_user_dao_test.xml");
        ApplicationContext context = new AnnotationConfigApplicationContext("ru.pavlytskaya");
        subj = context.getBean(TransactionInformationDao.class);
    }



    @Test(expected = CustomException.class)
    public void insert_limitExceeded() {
        subj.insert(1L, 0L, BigDecimal.valueOf(2000000), LocalDate.of(2020, 12, 11),1L);
    }

    @Test
    public void insert_ok() {
        TransactionInformationModel insert = subj.insert( 1L, 0L, BigDecimal.valueOf(2000), LocalDate.of(2020,12,11),1L);
        assertEquals(2, insert.getId());
        assertEquals(1, insert.getAccountFrom());
        assertEquals(null, insert.getAccountTo());
        assertEquals(2000, insert.getSum().intValue());
        assertEquals(LocalDate.of(2020,12,11), insert.getData());

    }

    @Test
    public  void not_delete(){
        int delete = subj.delete(3);
        assertEquals(0,delete);
    }

    @Test
    public void informationModelList_notFound() {
        List<TransactionInformationModel> informationModels = subj.informationModelList(1L, LocalDate.of(2020, 12, 11), LocalDate.of(2020, 12, 13));

        assertEquals(0, informationModels.size());

    }

    @Test
    public void informationModelList_ok() {
        List<TransactionInformationModel> informationModels = subj.informationModelList(1L, LocalDate.parse("2020-12-10"), LocalDate.parse("2020-12-19"));

        assertEquals(1, informationModels.get(0).getId());
        assertEquals(1, informationModels.get(0).getAccountFrom());
        assertEquals(0, informationModels.get(0).getAccountTo());
        assertEquals(2000, informationModels.get(0).getSum().intValue());
        assertEquals(LocalDate.parse("2020-12-11"), informationModels.get(0).getData());

    }
}