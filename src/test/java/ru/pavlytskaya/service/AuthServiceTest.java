package ru.pavlytskaya.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.pavlytskaya.converter.UserModelToUserDTOConverter;
import ru.pavlytskaya.dao.UserDao;
import ru.pavlytskaya.dao.UserModel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {
    @InjectMocks AuthService subj;
    @Mock UserDao userDao;
    @Mock DigestService digestService;
    @Mock UserModelToUserDTOConverter userDTOConverter;

    @Test
    public void auth_userNotFound() {
        when(digestService.hex("12345678")).thenReturn("hex");
        when(userDao.findByEmailAndHash("marinabushneva@gmail.com", "hex")).thenReturn(null);

        UserDTO user = subj.auth("marinabushneva@gmail.com", "12345678");

        assertNull(user);

        verify(digestService, times(1)).hex("12345678");
        verify(userDao, times(1)).findByEmailAndHash("marinabushneva@gmail.com", "hex");
        verifyNoMoreInteractions(userDTOConverter);

    }

    @Test
    public void auth_userFound() {
        when(digestService.hex("12345678")).thenReturn("hex");
        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setFirstName("Marina");
        userModel.setLastName("Pavlytskaya");
        userModel.setEmail("marinabushneva@gmail.com");
        userModel.setPassword("hex");

        when(userDao.findByEmailAndHash("marinabushneva@gmail.com", "hex")).thenReturn(userModel);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setFirstName("Marina");
        userDTO.setLastName("Pavlytskaya");
        userDTO.setEmail("marinabushneva@gmail.com");
        when(userDTOConverter.convert(userModel)).thenReturn(userDTO);

        UserDTO user = subj.auth("marinabushneva@gmail.com", "12345678");

        assertNotNull(user);
        assertEquals(userDTO, user);

        verify(digestService, times(1)).hex("12345678");
        verify(userDao, times(1)).findByEmailAndHash("marinabushneva@gmail.com", "hex");
        verify(userDTOConverter, times(1)).convert(userModel);


    }

    @Test
    public void registration_NotInformation() {
        when(digestService.hex("12345678")).thenReturn("hex");
        when(userDao.insert("Marina", "Pavlytskaya", "marinabushneva@gmail.com", "hex")).thenReturn(null);

        UserDTO user = subj.registration("Marina", "Pavlytskaya", "marinabushneva@gmail.com", "12345678");

        assertNull(user);

        verify(digestService, times(1)).hex("12345678");
        verify(userDao, times(1)).insert("Marina", "Pavlytskaya", "marinabushneva@gmail.com", "hex");
        verifyNoMoreInteractions(userDTOConverter);
    }

    @Test
    public void registration_Successful() {
        when(digestService.hex("12345678")).thenReturn("hex");
        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setFirstName("Marina");
        userModel.setLastName("Pavlytskaya");
        userModel.setEmail("marinabushneva@gmail.com");
        userModel.setPassword("hex");
        when(userDao.insert("Marina", "Pavlytskaya", "marinabushneva@gmail.com", "hex")).thenReturn(userModel);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setFirstName("Marina");
        userDTO.setLastName("Pavlytskaya");
        userDTO.setEmail("marinabushneva@gmail.com");
        when(userDTOConverter.convert(userModel)).thenReturn(userDTO);

        UserDTO user = subj.registration("Marina", "Pavlytskaya", "marinabushneva@gmail.com", "12345678");

        assertNotNull(user);
        assertEquals(userDTO, user);

        verify(digestService, times(1)).hex("12345678");
        verify(userDao, times(1)).insert("Marina", "Pavlytskaya", "marinabushneva@gmail.com", "hex");
        verify(userDTOConverter, times(1)).convert(userModel);
    }
}