package ru.pavlytskaya.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.pavlytskaya.converter.UserModelToUserDTOConverter;
import ru.pavlytskaya.entity.UserModel;
import ru.pavlytskaya.repository.UserModelRepository;

import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.*;


@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {
    @InjectMocks
    AuthService subj;
    @Mock
    UserModelRepository userModelRepository;
    @Mock
    DigestService digestService;
    @Mock
    UserModelToUserDTOConverter userDTOConverter;

    @Test
    public void auth_userNotFound() {
        when(digestService.hex("12345678")).thenReturn("hex");

        when(userModelRepository.findByEmailAndPassword("marinabushneva@gmail.com", "hex")).thenReturn(null);

        UserDTO user = subj.auth("marinabushneva@gmail.com", "12345678");

        assertNull(user);

        verify(digestService, times(1)).hex("12345678");
        verify(userModelRepository, times(1)).findByEmailAndPassword("marinabushneva@gmail.com", "hex");
        verifyNoMoreInteractions(userDTOConverter);

    }

    @Test
    public void auth_userFound() {
        when(digestService.hex("12345678")).thenReturn("hex");

        UserModel userModel = new UserModel().setId(1L).setFirstName("Marina").setLastName("Pavlytskaya")
                .setEmail("marinabushneva@gmail.com").setPassword("hex");

        doReturn(userModel).when(userModelRepository).findByEmailAndPassword("marinabushneva@gmail.com", "hex");

        UserDTO userDTO = new UserDTO().setId(1).setFirstName("Marina")
                .setLastName("Pavlytskaya").setEmail("marinabushneva@gmail.com");
        doReturn(userDTO).when(userDTOConverter).convert(userModel);

        UserDTO user = subj.auth("marinabushneva@gmail.com", "12345678");

        assertNotNull(user);
        assertEquals(userDTO, user);

        verify(digestService, times(1)).hex("12345678");
        verify(userModelRepository, times(1)).findByEmailAndPassword("marinabushneva@gmail.com", "hex");
        verify(userDTOConverter, times(1)).convert(userModel);
    }

    @Test
    public void registration_NotInformation() throws Exception {
        UserModel userModel = new UserModel();

        doReturn("hex").when(digestService).hex("12345678");

        doReturn(null).when(userModelRepository).save(userModel.setFirstName("Marina").setLastName("Pavlytskaya")
                .setEmail("marinabushneva@gmail.com").setPassword("hex"));

        UserDTO user = subj.registration("Marina", "Pavlytskaya", "marinabushneva@gmail.com", "12345678");

        assertNull(user);

        verify(digestService, times(1)).hex("12345678");
        verify(userModelRepository, times(1)).save(userModel);
        verify(userDTOConverter, times(1)).convert(userModel);
    }

    @Test
    public void registration_Successful() throws Exception {
        when(digestService.hex("12345678")).thenReturn("hex");

        UserModel userModel = new UserModel()
                .setFirstName("Marina").setLastName("Pavlytskaya")
                .setEmail("marinabushneva@gmail.com").setPassword("hex");
        doReturn(userModel).when(userModelRepository).save(userModel);

        UserDTO userDTO = new UserDTO().setId(1).setFirstName("Marina")
                .setLastName("Pavlytskaya").setEmail("marinabushneva@gmail.com");
        doReturn(userDTO).when(userDTOConverter).convert(userModel);

        UserDTO user = subj.registration("Marina", "Pavlytskaya", "marinabushneva@gmail.com", "12345678");

        assertNotNull(user);
        assertEquals(userDTO, user);

        verify(digestService, times(1)).hex("12345678");
        verify(userModelRepository, times(1)).save(userModel);
        verify(userDTOConverter, times(1)).convert(userModel);
    }
}