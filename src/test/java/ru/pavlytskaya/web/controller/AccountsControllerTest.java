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
import ru.pavlytskaya.service.AccountDTO;
import ru.pavlytskaya.service.AccountService;
import ru.pavlytskaya.web.form.AccountCreateForm;
import ru.pavlytskaya.web.form.AccountDeleteForm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountsController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class AccountsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    UserModelRepository userModelRepository;

    @Before
    public void setUp() throws Exception {
        when(userModelRepository.findUserById(1L)).thenReturn(
                new UserModel().setId(1L)
                        .setFirstName("Marina")
                        .setLastName("Pavlytska")
                        .setEmail("marinabushneva@gmail.com")
                        .setPassword("12345678")
                        .setRoles(Collections.singleton(UserRole.USER))
        );
    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void accountInfo() throws Exception {

        List<AccountDTO> accounts = new ArrayList<>();
        accounts.add(new AccountDTO()
                .setNameAccount("main")
                .setBalance(BigDecimal.valueOf(1000000))
                .setCurrency("$")
                .setId(1));
        when(accountService.accountInformation(1L)).thenReturn(accounts);

        mockMvc.perform(get("/accountInfo"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("items", accounts))
                .andExpect(view().name("accountInfo"));

    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getDeleteAccount() throws Exception {
        mockMvc.perform(get("/delete")
                        .flashAttr("form", new AccountDeleteForm()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/delete"));
    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void deleteAccount() throws Exception {

        mockMvc.perform(post("/delete" + 1))
                .andDo(print())
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/accountInfo"));

    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getCreateAccount() throws Exception {

        mockMvc.perform(get("/create").flashAttr("form", new AccountCreateForm()))
                .andExpect(status().isOk())
                .andExpect(view().name("create"));

    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postCreateAccount() throws Exception {

        when(accountService.accountCreat("travel", BigDecimal.valueOf(1000000), "$", 1L))
                .thenReturn(new AccountDTO().setId(1).setNameAccount("travel")
                        .setBalance(BigDecimal.valueOf(1000000)).setCurrency("$"));

        mockMvc.perform(post("/create")
                        .flashAttr("form", new AccountCreateForm()
                                .setNameAccount("travel")
                                .setBalance(BigDecimal.valueOf(1000000))
                                .setCurrency("$")))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/accountInfo"));
    }
}