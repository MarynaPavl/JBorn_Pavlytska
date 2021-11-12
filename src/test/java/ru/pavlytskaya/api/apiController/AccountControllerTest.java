package ru.pavlytskaya.api.apiController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.pavlytskaya.MockSecurityConfiguration;
import ru.pavlytskaya.api.apiConverter.AccountModelToResponseConverter;
import ru.pavlytskaya.config.SecurityConfiguration;
import ru.pavlytskaya.entity.UserModel;
import ru.pavlytskaya.repository.UserModelRepository;
import ru.pavlytskaya.security.CustomUserDetailsService;
import ru.pavlytskaya.security.UserRole;
import ru.pavlytskaya.service.AccountDTO;
import ru.pavlytskaya.service.AccountService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;


    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    UserModelRepository userModelRepository;

    @SpyBean
    AccountModelToResponseConverter converter;

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
    public void getAccountInfo() throws Exception {
        List<AccountDTO> accounts = new ArrayList<>();
        accounts.add(new AccountDTO()
                .setId(1)
                .setNameAccount("main")
                .setBalance(BigDecimal.valueOf(1000000))
                .setCurrency("$"));

        when(accountService.accountInformation(1L)).thenReturn(accounts);

        mockMvc.perform(get("/api/get-account-info"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"accountDTO\":[{\"id\":1,\"nameAccount\":\"main\",\"balance\":1000000,\"currency\":\"$\"}]}"));

    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void createAccount() throws Exception {
        when(accountService.accountCreat("save", BigDecimal.valueOf(100000), "$", 1L))
                .thenReturn(new AccountDTO().setId(1).setNameAccount("save")
                        .setBalance(BigDecimal.valueOf(100000)) .setCurrency("$"));

        mockMvc.perform(post("/api//account-create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nameAccount\" : \"save\",\"balance\" : \"100000\",\"currency\" : \"$\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"accountDTO\" : {\"id\" : 1,\"nameAccount\" : \"save\"," +
                        "\"balance\" : 100000,\"currency\" : \"$\"}}"));

    }


    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void deleteAccount() throws Exception {
        when(accountService.deleteAccount(1)).thenReturn(1);

        mockMvc.perform(delete("/api/account-delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountId\" : 1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("successfully"));
    }
}