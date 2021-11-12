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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.pavlytskaya.MockSecurityConfiguration;
import ru.pavlytskaya.api.apiConverter.UserModelToResponseConverter;
import ru.pavlytskaya.config.SecurityConfiguration;
import ru.pavlytskaya.repository.UserModelRepository;
import ru.pavlytskaya.security.CustomUserDetailsService;
import ru.pavlytskaya.service.AuthService;
import ru.pavlytskaya.service.UserDTO;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class LoginControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserModelRepository userModelRepository;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    AuthService authService;

    @SpyBean
    UserModelToResponseConverter converter;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void registration() throws Exception {
        UserDTO user = new UserDTO().setId(2)
                        .setFirstName("Ivan")
                                .setLastName("Ivanov")
                                        .setEmail("IvanIvanov@gmail.com");
        when(authService.registration("Ivan", "Ivanov", "IvanIvanov@gmail.com", "12345678")).thenReturn(user);

        mockMvc.perform(post("/api//registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\" : \"Ivan\",\"lastName\" : \"Ivanov\",\"email\" : \"IvanIvanov@gmail.com\",\"password\" :  \"12345678\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"id\"" +
                        " : 2,\n" +
                        "  \"firstName\" : \"Ivan\",\n" +
                        "  \"lastName\" : \"Ivanov\",\n" +
                        "  \"email\" : \"IvanIvanov@gmail.com\"\n" +
                        "}"));
    }
}