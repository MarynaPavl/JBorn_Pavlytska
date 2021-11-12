package ru.pavlytskaya.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.pavlytskaya.MockSecurityConfiguration;
import ru.pavlytskaya.config.SecurityConfiguration;
import ru.pavlytskaya.entity.UserModel;
import ru.pavlytskaya.repository.UserModelRepository;
import ru.pavlytskaya.security.CustomUserDetailsService;
import ru.pavlytskaya.security.UserRole;
import ru.pavlytskaya.service.AuthService;
import ru.pavlytskaya.web.form.RegistrationForm;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserModelRepository userModelRepository;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    AuthService authService;


    @Before
    public void setUp() throws Exception {
    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void index() throws Exception {
        when(userModelRepository.findUserById(1L)).thenReturn(
                new UserModel().setId(1L)
                        .setFirstName("Marina")
                        .setLastName("Pavlytska")
                        .setEmail("marinabushneva@gmail.com")
                        .setPassword("12345678")
                        .setRoles(Collections.singleton(UserRole.USER))
        );
        mockMvc.perform(get("/personal-area"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("name", "Marina"))
                .andExpect(model().attribute("surname", "Pavlytska"))
                .andExpect(model().attribute("userId", 1L))
                .andExpect(view().name("personal-area"));
    }

    @Test
    public void getLogin() throws Exception {
        mockMvc.perform(get("/login-form"))
                .andExpect(status().isOk())
                .andExpect(view().name("login-form"));
    }

    @Test
    public void getRegistration() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    public void postRegistration() throws Exception {
        mockMvc.perform(post("/registration")
                        .flashAttr("form", new RegistrationForm()
                                .setFirstName("Ivan")
                                .setLastName("Ivanov")
                                .setEmail("IvanIvanov@gmail.com")
                                .setPassword("12345678")))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/login-form"));

    }
}