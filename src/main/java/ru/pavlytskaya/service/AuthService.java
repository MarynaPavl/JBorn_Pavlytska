package ru.pavlytskaya.service;

import org.springframework.stereotype.Service;
import ru.pavlytskaya.converter.Converter;
import ru.pavlytskaya.dao.UserDao;
import ru.pavlytskaya.dao.UserModel;

@Service
public class AuthService {
    private final UserDao userDao;
    private final DigestService digestService;
    private final Converter<UserModel, UserDTO> userDTOConverter;

    public AuthService(UserDao userDao, DigestService digestService, Converter<UserModel, UserDTO> userDTOConverter) {
        this.userDao = userDao;
        this.digestService = digestService;
        this.userDTOConverter = userDTOConverter;
    }

    public UserDTO auth(String email, String password) {
        String hash = digestService.hex(password);

        UserModel userModel = userDao.findByEmailAndHash(email, hash);
        if (userModel == null) {
            return null;
        }
        return userDTOConverter.convert(userModel);
    }

    public UserDTO registration(String firstName, String lastName, String email, String password) {
        String hash = digestService.hex(password);

        UserModel userModel = userDao.insert(firstName, lastName, email, hash);
        if (userModel == null) {
            return null;
        }
        return userDTOConverter.convert(userModel);
    }

}