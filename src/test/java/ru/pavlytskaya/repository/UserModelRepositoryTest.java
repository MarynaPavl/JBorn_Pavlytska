package ru.pavlytskaya.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pavlytskaya.entity.UserModel;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ru.pavlytskaya.security.UserRole.USER;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserModelRepositoryTest {

    @Autowired UserModelRepository subj;
    @Autowired
    EntityManager em;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByEmail() {
        UserModel user = subj.findByEmail("marinabushneva@gmail.com");

        assertNotNull(user);

        assertEquals(Long.valueOf(1L), user.getId());
        assertEquals("Marina", user.getFirstName());
        assertEquals("Pavlytskaya", user.getLastName());
        assertEquals("marinabushneva@gmail.com", user.getEmail());
        assertEquals("$2a$10$2Hej4e3zZAjsawBBJ4lxnOgRZK1NkqyKhg/4rc6SdbBMW5QfcRsp.", user.getPassword());
        assertEquals(1, user.getAccounts().size());
        assertEquals(USER, user.getRoles().iterator().next());//new CustomGrantedAuthority(UserRole.USER), userDetails.getAuthorities().iterator().next()
    }

    @Test
    public void findUserById() {
    }
}