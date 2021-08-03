package ru.pavlytskaya.converter;

import org.springframework.stereotype.Service;
import ru.pavlytskaya.entity.UserModel;
import ru.pavlytskaya.service.UserDTO;
@Service
public class UserModelToUserDTOConverter implements Converter<UserModel, UserDTO> {
    @Override
    public UserDTO convert(UserModel source) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(source.getId());
        userDTO.setFirstName(source.getFirstName());
        userDTO.setLastName(source.getLastName());
        userDTO.setEmail(source.getEmail());
        userDTO.setAccounts(source.getAccounts());
        return userDTO;
    }
}
