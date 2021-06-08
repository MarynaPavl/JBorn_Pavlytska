//package ru.pavlytskaya.dao;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//import javax.persistence.NoResultException;
//import javax.persistence.PersistenceException;
//import java.util.UUID;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNull;
//
//public class UserDaoTest {
//    UserDao subj;
//    @Before
//    public void setUp() throws Exception {
//        System.setProperty("jdbcUrl","jdbc:h2:mem:test_mem" + UUID.randomUUID().toString() + ";MODE=PostgreSQL;DB_CLOSE_DELAY=-1");
//        System.setProperty("jdbcUser","sa");
//        System.setProperty("jdbcPassword","");
//        System.setProperty("dialect","org.hibernate.dialect.H2Dialect");
//        System.setProperty("liquibaseFile","liquibase_user_dao_test.xml");
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ru.pavlytskaya");
//        subj = context.getBean(UserDao.class);
//    }
//
//
//    @Test
//    public void findByEmailAndHash() {
//        UserModel user =subj.findByEmailAndHash("marinabushneva@gmail.com", "25d55ad283aa400af464c76d713c07ad");
//        assertEquals(1L, user.getId());
//        assertEquals("marinabushneva@gmail.com", user.getEmail());
//        assertEquals("25d55ad283aa400af464c76d713c07ad", user.getPassword());
//    }
//
//    @Test(expected = NoResultException.class)
//    public void findByEmailAndHash_notFound() {
//        UserModel user =subj.findByEmailAndHash("marinabushneva@gmail.com", "35d55ad283aa400af464c76d713c07ad");
//        assertNull(user);
//    }
//
//    @Test(expected = PersistenceException.class)
//    public void insert_notUnique() {
//       subj.insert("Marina", "Pavlytskaya","marinabushneva@gmail.com","25d55ad283aa400af464c76d713c07ad");
//    }
//
//    @Test
//    public void insert_ok() {
//        UserModel user = subj.insert("Ivan", "Ivanov","IvanovI@gmail.com","25d55ad283aa400af464c76d713c07ad");
//
//        assertEquals(2L, user.getId());
//        assertEquals("Ivan", user.getFirstName());
//        assertEquals("Ivanov", user.getLastName());
//        assertEquals("IvanovI@gmail.com", user.getEmail());
//        assertEquals("25d55ad283aa400af464c76d713c07ad", user.getPassword());
//    }
//
//}