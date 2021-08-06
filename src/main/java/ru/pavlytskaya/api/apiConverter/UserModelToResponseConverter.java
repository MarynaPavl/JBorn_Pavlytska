package ru.pavlytskaya.api.apiConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.pavlytskaya.api.json.AuthResponse;
import ru.pavlytskaya.service.UserDTO;

@Component
public class UserModelToResponseConverter implements Converter<UserDTO, AuthResponse> {

    @Override
    public AuthResponse convert(UserDTO user) {
        return new AuthResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

}
