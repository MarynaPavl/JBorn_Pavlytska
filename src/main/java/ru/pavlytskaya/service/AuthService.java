package ru.pavlytskaya.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pavlytskaya.converter.Converter;
import ru.pavlytskaya.entity.UserModel;
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

    @Transactional
    public UserDTO registration(String firstName, String lastName, String email, String password) throws Exception {
        String hash = digestService.hex(password);
        UserModel userModel = new UserModel()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(hash);

        try {
             userModelRepository.save(userModel);
        } catch (Error | Exception ex) {
            throw new Exception(ex.getCause());
        }

        return userDTOConverter.convert(userModel);
    }

}