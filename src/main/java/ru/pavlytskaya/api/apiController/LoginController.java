package ru.pavlytskaya.api.apiController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pavlytskaya.api.apiConverter.UserModelToResponseConverter;
import ru.pavlytskaya.api.json.AuthResponse;
import ru.pavlytskaya.api.json.RegistrationRequest;
import ru.pavlytskaya.service.AuthService;
import ru.pavlytskaya.service.UserDTO;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {

    private final UserModelToResponseConverter converter;
    private final AuthService authService;

    @PostMapping("/registration")
    public @ResponseBody
    ResponseEntity<AuthResponse> registration(@RequestBody @Valid RegistrationRequest request) throws Exception {
        UserDTO user = authService.registration(request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword());

        if (user == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        return ok(converter.convert(user));
    }
}
