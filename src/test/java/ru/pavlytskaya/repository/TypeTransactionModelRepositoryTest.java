package ru.pavlytskaya.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pavlytskaya.entity.TypeTransactionModel;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TypeTransactionModelRepositoryTest {

    @Autowired TypeTransactionModelRepository subj;
    @Autowired
    EntityManager em;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findTypeTransactionModelById() {
        TypeTransactionModel typeTransactionModelById = subj.findTypeTransactionModelById(1L);

        assertNotNull(typeTransactionModelById);

        assertEquals(1L, typeTransactionModelById.getId());
        assertEquals("food", typeTransactionModelById.getAssignment());
    }

    @Test
    public void deleteById() {
        subj.deleteById(1L);

        TypeTransactionModel typeTransactionModelById = subj.findTypeTransactionModelById(1L);

        assertNull(typeTransactionModelById);
    }

    @Test
    public void findTypeByFilter() {
        TypeTransactionModelFilter filter = new TypeTransactionModelFilter().setAssignmentLike("%food%");

        List<TypeTransactionModel> types = subj.findByFilter(filter);

        assertNotNull(types);
        assertEquals(1, types.size());
    }
}