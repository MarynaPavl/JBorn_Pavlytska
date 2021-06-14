package ru.pavlytskaya.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.converter.Converter;
import ru.pavlytskaya.dao.UserModel;
import ru.pavlytskaya.repository.UserModelRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserModelRepository userModelRepository;
    private final DigestService digestService;
    private final Converter<UserModel, UserDTO> userDTOConverter;

    public UserDTO auth(String email, String password) {
        String hash = digestService.hex(password);

        UserModel userModel = userModelRepository.findByEmailAndPassword(email, hash);
        if (userModel == null) {
            return null;
        }
        return userDTOConverter.convert(userModel);
    }

    public UserDTO registration(String firstName, String lastName, String email, String password) {
        String hash = digestService.hex(password);
        UserModel userModel = new UserModel();
              userModel.setFirstName(firstName);
              userModel.setLastName(lastName);
              userModel.setEmail(email);
              userModel.setPassword(hash);

      userModelRepository.save(userModel);
        return userDTOConverter.convert(userModel);
    }

}