package ru.pavlytskaya.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pavlytskaya.entity.AccountModel;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class AccountModelRepositoryTest {

    @Autowired AccountModelRepository subj;

    @Autowired
    EntityManager em;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findAllByUserId() {
        List<AccountModel> allByUserId = subj.findAllByUserId(1L);

        assertNotNull(allByUserId);

        assertEquals(1, allByUserId.size());
        assertEquals("main", allByUserId.get(0).getNameAccount());
        assertEquals(BigDecimal.valueOf(100000000).movePointLeft(2), allByUserId.get(0).getBalance());
        assertEquals("$", allByUserId.get(0).getCurrency());
        assertEquals(Long.valueOf(1L), allByUserId.get(0).getUser().getId());

    }

    @Test
    public void deleteById() {
        subj.deleteById(1L);
        AccountModel accountModelById = subj.findAccountModelById(1L);

        assertNull(accountModelById);
    }

    @Test
    public void findAccountModelById() {
        AccountModel accountModelById = subj.findAccountModelById(1L);

        assertNotNull(accountModelById);

        assertEquals(1L, accountModelById.getId());
        assertEquals("main", accountModelById.getNameAccount());
        assertEquals(BigDecimal.valueOf(100000000).movePointLeft(2), accountModelById.getBalance());
        assertEquals("$", accountModelById.getCurrency());
        assertEquals(Long.valueOf(1L), accountModelById.getUser().getId());

    }
}