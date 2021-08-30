package ru.pavlytskaya.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.api.json.AuthRequest;
import ru.pavlytskaya.api.json.AuthResponse;
import ru.pavlytskaya.service.AuthService;
import ru.pavlytskaya.service.UserDTO;

@Service("/login")

@RequiredArgsConstructor
public class AuthController implements Controller<AuthRequest, AuthResponse> {
    private final AuthService authService;

    @Override
    public AuthResponse handler(AuthRequest request) {
        UserDTO userDTO = authService.auth(request.getEmail());
        if (userDTO != null) {
            return new AuthResponse(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail());
        }
        return null;
    }

    @Override
    public Class<AuthRequest> getRequestClass() {
        return AuthRequest.class;
    }

}
