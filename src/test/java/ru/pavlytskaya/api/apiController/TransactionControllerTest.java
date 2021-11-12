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
import ru.pavlytskaya.api.apiConverter.TransactionModelToResponseConverter;
import ru.pavlytskaya.config.SecurityConfiguration;
import ru.pavlytskaya.entity.UserModel;
import ru.pavlytskaya.repository.UserModelRepository;
import ru.pavlytskaya.security.CustomUserDetailsService;
import ru.pavlytskaya.security.UserRole;
import ru.pavlytskaya.service.TransactionInformationDTO;
import ru.pavlytskaya.service.TransactionInformationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class TransactionControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionInformationService transactionInformationService;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    UserModelRepository userModelRepository;

    @SpyBean
    TransactionModelToResponseConverter converter;

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
    public void transactionInformation() throws Exception {
        List<TransactionInformationDTO> list = new ArrayList<>();
        TransactionInformationDTO transaction = new TransactionInformationDTO()
                .setId(1)
                .setTransfer("expense")
                .setSum(BigDecimal.valueOf(1000))
                .setData(LocalDate.parse("2021-10-12"));
        list.add(transaction);

        when(transactionInformationService
                .informationModels("food", LocalDate.parse("2021-10-10"),
                        LocalDate.parse("2021-10-15")))
                .thenReturn(list);
        mockMvc.perform(get("/api/get-transaction-info")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"assignment\" : \"food\"," +
                        "\"fromDate\" : \"2021-10-10\"," +
                        "\"toData\" : \"2021-10-15\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"information\" : [{\"id\" : 1, \"transfer\" : \"expense\"," +
                        "\"sum\" :  1000, \"data\" : \"2021-10-12\"}]}"));
    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void createTransaction() throws Exception {
        Set<Long> assignment = new HashSet<>();
        assignment.add(1L);
        when(transactionInformationService.transactionInsert(1L,0L,BigDecimal.valueOf(1000), LocalDate.parse("2021-10-12"), assignment)).thenReturn(new TransactionInformationDTO()
                .setId(1).setTransfer("expense")
                .setSum(BigDecimal.valueOf(1000))
                .setData(LocalDate.parse("2021-10-12")));

        mockMvc.perform(post("/api/transaction-create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountFrom\" : 1,\"accountTo\" : 0,\"sum\" : 1000,\"data\" : \"2021-10-12\",\"assignmentId\" : [1]}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\" : 1,\"transfer\" : \"expense\"," +
                        "\"sum\" : 1000,\"data\" : \"2021-10-12\"}"));
    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void deleteTransaction() throws Exception {
        when(transactionInformationService.deleteTransaction(1L)).thenReturn(1);

        mockMvc.perform(delete("/api/transaction-delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"transactionId\" : 1}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("successfully"));
    }
}