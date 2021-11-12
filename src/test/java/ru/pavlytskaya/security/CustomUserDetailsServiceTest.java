package ru.pavlytskaya.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class CustomUserDetailsServiceTest {
    @Autowired CustomUserDetailsService subj;

    @Test
    public void loadUserByUsername() {
        UserDetails userDetails = subj.loadUserByUsername("marinabushneva@gmail.com");

        assertNotNull(userDetails);
        assertEquals("marinabushneva@gmail.com", userDetails.getUsername());
        assertEquals("$2a$10$Uhu2iaprCMLvv4k.jEgYzOnO4WTx2ZNjbziDm30yC3jPELm5amHsO", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals(new CustomGrantedAuthority(UserRole.USER), userDetails.getAuthorities().iterator().next());
    }
}