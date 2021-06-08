package ru.pavlytskaya.webController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.pavlytskaya.dao.UserModel;
import ru.pavlytskaya.json.AuthRequest;
import ru.pavlytskaya.json.AuthResponse;
import ru.pavlytskaya.repository.UserModelRepository;
import ru.pavlytskaya.webConverter.UserModelToResponseConverter;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class LoginController {
    private final UserModelRepository userModelRepository;
    private final UserModelToResponseConverter converter;
    @PostMapping("/login")
    public @ResponseBody AuthResponse login(@RequestBody @Valid AuthRequest request){
        UserModel user = userModelRepository.getOne(1L);
        return converter.convert(user);
    }
}
