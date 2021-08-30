package ru.pavlytskaya.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.pavlytskaya.converter.UserModelToUserDTOConverter;
import ru.pavlytskaya.entity.UserModel;
import ru.pavlytskaya.repository.UserModelRepository;
import ru.pavlytskaya.security.UserRole;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.*;
import static ru.pavlytskaya.security.UserRole.USER;


@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {
    @InjectMocks
    AuthService subj;
    @Mock
    UserModelRepository userModelRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserModelToUserDTOConverter userDTOConverter;


    @Test
    public void registration_NotInformation() throws Exception {
        UserModel userModel = new UserModel();
        Set<UserRole> roleSet = new HashSet<>();
        roleSet.add(USER);

        doReturn("hex").when(passwordEncoder).encode("12345678");

        doReturn(null).when(userModelRepository).save(userModel.setFirstName("Marina").setLastName("Pavlytskaya")
                .setEmail("marinabushneva@gmail.com").setPassword("hex").setRoles(roleSet));

        UserDTO user = subj.registration("Marina", "Pavlytskaya", "marinabushneva@gmail.com", "12345678");

        assertNull(user);

        verify(passwordEncoder, times(1)).encode("12345678");
        verify(userModelRepository, times(1)).save(userModel);
        verify(userDTOConverter, times(1)).convert(userModel);
    }

    @Test
    public void registration_Successful() throws Exception {
        when(passwordEncoder.encode("12345678")).thenReturn("hex");
        Set<UserRole> roleSet = new HashSet<>();
        roleSet.add(USER);

        UserModel userModel = new UserModel()
                .setFirstName("Marina").setLastName("Pavlytskaya")
                .setEmail("marinabushneva@gmail.com").setPassword("hex").setRoles(roleSet);
        doReturn(userModel).when(userModelRepository).save(userModel);

        UserDTO userDTO = new UserDTO().setId(1).setFirstName("Marina")
                .setLastName("Pavlytskaya").setEmail("marinabushneva@gmail.com").setRoles(roleSet);
        doReturn(userDTO).when(userDTOConverter).convert(userModel);

        UserDTO user = subj.registration("Marina", "Pavlytskaya", "marinabushneva@gmail.com", "12345678");

        assertNotNull(user);
        assertEquals(userDTO, user);

        verify(passwordEncoder, times(1)).encode("12345678");
        verify(userModelRepository, times(1)).save(userModel);
        verify(userDTOConverter, times(1)).convert(userModel);
    }
}