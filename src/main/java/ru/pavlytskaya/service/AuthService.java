package ru.pavlytskaya.service;

import ru.pavlytskaya.comverter.UserModelToUserDTOConverter;
import ru.pavlytskaya.dao.UserDao;
import ru.pavlytskaya.dao.UserModel;

public class AuthService {
    private final UserDao userDao;
    private final DigestService digestService;
    private final UserModelToUserDTOConverter userDTOConverter;


    public AuthService() {
        this.userDao = new UserDao();
        this.digestService = new Md5DigestService();
        this.userDTOConverter = new UserModelToUserDTOConverter();

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