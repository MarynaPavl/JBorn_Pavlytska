package ru.pavlytskaya.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pavlytskaya.converter.Converter;
import ru.pavlytskaya.entity.UserModel;
import ru.pavlytskaya.repository.UserModelRepository;
import ru.pavlytskaya.security.UserRole;

import java.util.HashSet;
import java.util.Set;

import static ru.pavlytskaya.security.UserRole.USER;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserModelRepository userModelRepository;
    private final Converter<UserModel, UserDTO> userDTOConverter;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserDTO auth(String email) {

        UserModel userModel = userModelRepository.findByEmail(email);
        if (userModel == null) {
            return null;
        }
        return userDTOConverter.convert(userModel);
    }

    @Transactional
    public UserDTO registration(String firstName, String lastName, String email, String password) throws Exception {

        Set<UserRole> roleSet = new HashSet<>();
        roleSet.add(USER);
        UserModel userModel = new UserModel()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(passwordEncoder.encode(password))
                .setRoles(roleSet);

        try {
             userModelRepository.save(userModel);
        } catch (Error | Exception ex) {
            throw new Exception(ex.getCause());
        }

        return userDTOConverter.convert(userModel);
    }



}