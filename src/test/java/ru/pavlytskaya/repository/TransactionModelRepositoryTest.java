package ru.pavlytskaya.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pavlytskaya.entity.TransactionInformationModel;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TransactionModelRepositoryTest {

    @Autowired TransactionModelRepository subj;
    @Autowired
    EntityManager em;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void deleteById() {
        subj.deleteById(1L);

        TransactionModelFilter filter = new TransactionModelFilter().setAssignmentLike("%food%")
                .setFromData(LocalDate.of(2020, 12, 1)).setToData(LocalDate.of(2021, 12, 1));
        List<TransactionInformationModel> modelList = subj.findByFilter(filter);

        System.out.println(modelList);

        assertEquals(0, modelList.size());
    }

    @Test
    public void findTransactionByFilter() {
        TransactionModelFilter filter = new TransactionModelFilter().setAssignmentLike("%food%")
                .setFromData(LocalDate.of(2020, 12, 1)).setToData(LocalDate.of(2021, 12, 1));

        List<TransactionInformationModel> transactions = subj.findByFilter(filter);

        assertNotNull(transactions);
        assertEquals(1, transactions.size());
    }
}