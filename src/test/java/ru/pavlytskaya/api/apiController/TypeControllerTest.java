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
import ru.pavlytskaya.api.apiConverter.TypeTransactionModelToResponseConverter;
import ru.pavlytskaya.config.SecurityConfiguration;
import ru.pavlytskaya.entity.UserModel;
import ru.pavlytskaya.repository.UserModelRepository;
import ru.pavlytskaya.security.CustomUserDetailsService;
import ru.pavlytskaya.security.UserRole;
import ru.pavlytskaya.service.TypeDTO;
import ru.pavlytskaya.service.TypeService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TypeController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class TypeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TypeService typeService;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    UserModelRepository userModelRepository;

    @SpyBean
    TypeTransactionModelToResponseConverter converter;

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
    public void typeInformation() throws Exception {
        List<TypeDTO> list = new ArrayList<>();
        TypeDTO type = new TypeDTO()
                .setId(1).setAssignment("food");
        list.add(type);
        when(typeService.typeInformation("food")).thenReturn(list);

        mockMvc.perform(get("/api/get-type-info")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"string\" : \"food\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"types\" : [{\"id\" : 1, \"assignment\" : \"food\"}]}"));
    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void typeCreate() throws Exception {
        when(typeService.typeCreat("food")).thenReturn(new TypeDTO()
                .setId(1).setAssignment("food"));

        mockMvc.perform(post("/api/type-create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"assignment\" : \"food\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\" : 1,\"assignment\" : \"food\"}"));
    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void typeDelete() throws Exception {
        when(typeService.delete(1L)).thenReturn(1);

        mockMvc.perform(delete("/api/type-delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"assigmentId\" : 1}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("successfully"));
    }
}