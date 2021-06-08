package ru.pavlytskaya.webConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.pavlytskaya.dao.UserModel;
import ru.pavlytskaya.json.AuthResponse;

@Component
public class UserModelToResponseConverter implements Converter<UserModel, AuthResponse> {
    @Override
    public AuthResponse convert(UserModel userModel) {
        return new AuthResponse(userModel.getId(), userModel.getFirstName(), userModel.getLastName(), userModel.getEmail());
    }
}
