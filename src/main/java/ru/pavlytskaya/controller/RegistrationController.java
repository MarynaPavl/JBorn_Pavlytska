package ru.pavlytskaya.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.json.RegistrationRequest;
import ru.pavlytskaya.json.RegistrationResponse;
import ru.pavlytskaya.service.AuthService;
import ru.pavlytskaya.service.UserDTO;

@Service("/registration")
@RequiredArgsConstructor
public class RegistrationController implements Controller<RegistrationRequest, RegistrationResponse> {
    private final AuthService authService;

    @Override
    public RegistrationResponse handler(RegistrationRequest request) {
        UserDTO userDTO = authService.registration(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword());
        if (userDTO != null) {
            return new RegistrationResponse(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail());
        }
        return null;
    }

    @Override
    public Class<RegistrationRequest> getRequestClass() {

        return RegistrationRequest.class;
    }
}
