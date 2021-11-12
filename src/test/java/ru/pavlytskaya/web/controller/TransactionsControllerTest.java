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
import ru.pavlytskaya.service.TransactionInformationDTO;
import ru.pavlytskaya.service.TransactionInformationService;
import ru.pavlytskaya.web.form.AccountDeleteForm;
import ru.pavlytskaya.web.form.TransactionCreateForm;
import ru.pavlytskaya.web.form.TransactionInformationForm;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionsController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class TransactionsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionInformationService transactionService;

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
    public void getTransactionInfo() throws Exception {

        mockMvc.perform(get("/transaction").flashAttr("form", new TransactionInformationForm()))
                .andExpect(status().isOk())
                .andExpect(view().name("transaction"));
    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postTypeInfo() throws Exception {
        List<TransactionInformationDTO> list = new ArrayList<>();
        list.add(new TransactionInformationDTO()
                .setId(1L)
                .setTransfer("expense")
                .setSum(BigDecimal.valueOf(2000))
                .setData(LocalDate.of(2020, 12, 11))
        );

        when(transactionService.informationModels("food", LocalDate.of(2020, 12, 01), LocalDate.of(2020, 12, 19))).thenReturn(list);

        mockMvc.perform(post("/transaction")
                        .flashAttr("form", new TransactionInformationForm()
                                .setAssignment("food")
                                .setFromDate("2020-12-01")
                                .setToData("2020-12-19")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("lastSearch", "food"))
                .andExpect(model().attribute("dataFrom", "2020-12-01"))
                .andExpect(model().attribute("dataTo", "2020-12-19"))
                .andExpect(model().attribute("types", list))
                .andExpect(view().name("transaction"));
    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getDeleteTransaction() throws Exception {
        mockMvc.perform(get("/deleteTransaction")
                        .flashAttr("form", new AccountDeleteForm()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/deleteTransaction"));

    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void deleteTransaction() throws Exception {

        mockMvc.perform(post("/deleteTransaction" + 1))
                .andDo(print())
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/transaction"));
    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getCreateTransaction() throws Exception {

        mockMvc.perform(get("/createTransaction")
                        .flashAttr("form", new TransactionCreateForm()))
                .andExpect(status().isOk())
                .andExpect(view().name("createTransaction"));
    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postCreateTransaction() throws Exception {
        Set<Long> assignment = new HashSet<>();
        assignment.add(1L);
        TransactionInformationDTO transaction = new TransactionInformationDTO()
                .setId(1)
                .setTransfer("expense")
                .setSum(BigDecimal.valueOf(2000))
                .setData(LocalDate.of(2020, 12, 11));

        when(transactionService.transactionInsert(
                1L, 0L, BigDecimal.valueOf(2000), LocalDate.of(2020, 12, 11), assignment))
                .thenReturn(transaction);

        mockMvc.perform(post("/createTransaction")
                        .flashAttr("form", new TransactionCreateForm()
                                .setAccountFrom(1L)
                                .setAccountTo(0L)
                                .setSum(BigDecimal.valueOf(2000))
                                .setData("2020-12-11")
                                .setAssignmentId(assignment)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("successfully", "Transaction was successfully created."))
                .andExpect(view().name("createTransaction"));
    }
}