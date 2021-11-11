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
import ru.pavlytskaya.service.TypeDTO;
import ru.pavlytskaya.service.TypeService;
import ru.pavlytskaya.web.form.TypeCreateForm;
import ru.pavlytskaya.web.form.TypeDeleteForm;
import ru.pavlytskaya.web.form.TypeInformationForm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TypesController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class TypesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TypeService typeService;

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
    public void getTypeInfo() throws Exception {

        mockMvc.perform(get("/type")
                        .flashAttr("form", new TypeInformationForm()))
                .andExpect(status().isOk())
                .andExpect(view().name("type"));
    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postTypeInfo() throws Exception {
        List<TypeDTO> types = new ArrayList<>();
        types.add(new TypeDTO().setId(1).setAssignment("food"));

        when(typeService.typeInformation("fo")).thenReturn(types);

        mockMvc.perform(post("/type")
                        .flashAttr("form", new TypeInformationForm()
                                .setString("fo")))
                .andExpect(model().attribute("lastSearch", "fo"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("types", types))
                .andExpect(view().name("type"));

    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getDeleteType() throws Exception {

        mockMvc.perform(get("/deleteType")
                        .flashAttr("form", new TypeDeleteForm()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/deleteType"));
    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void deleteType() throws Exception {

        mockMvc.perform(post("/deleteType" + 1))
                .andDo(print())
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/type"));

    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getCreateType() throws Exception {

        mockMvc.perform(get("/createType")
                        .flashAttr("form", new TypeCreateForm()))
                .andExpect(status().isOk())
                .andExpect(view().name("createType"));
    }

    @WithUserDetails(value = "marinabushneva@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postCreateType() throws Exception {

        when(typeService.typeCreat("food")).thenReturn(new TypeDTO()
                .setId(1).setAssignment("food"));

        mockMvc.perform(post("/createType")
                        .flashAttr("form", new TypeCreateForm()
                                .setAssignment("food")))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/type"));
    }
}