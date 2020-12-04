package ru.pavlytskaya.converter;

import ru.pavlytskaya.dao.UserModel;
import ru.pavlytskaya.service.UserDTO;

public class UserModelToUserDTOConverter implements Converter<UserModel, UserDTO> {
    @Override
    public UserDTO convert(UserModel source) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(source.getId());
        userDTO.setFirstName(source.getFirstName());
        userDTO.setLastName(source.getLastName());
        userDTO.setEmail(source.getEmail());
        return userDTO;
    }
}
