package ru.pavlytskaya.webController;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pavlytskaya.dao.UserModel;
import ru.pavlytskaya.json.AuthRequest;
import ru.pavlytskaya.json.AuthResponse;
import ru.pavlytskaya.repository.UserModelRepository;
import ru.pavlytskaya.webConverter.UserModelToResponseConverter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.*;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final UserModelRepository userModelRepository;
    private final UserModelToResponseConverter converter;

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request,
                                                            HttpServletRequest servletRequest){
        UserModel user = userModelRepository.findByEmailAndPassword(
                request.getEmail(),
                DigestUtils.md5Hex(request.getPassword()));
        if(user == null){
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        HttpSession session = servletRequest.getSession();
        session.setAttribute("userId", user.getId());
        return ok(converter.convert(user));
    }

    @GetMapping("/get-user-info")
    public @ResponseBody ResponseEntity<AuthResponse> getUserInfo(HttpServletRequest servletRequest){
        HttpSession session = servletRequest.getSession();
        Long userId =(Long)session.getAttribute("userId");
        if(userId == null){
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        UserModel user = userModelRepository.getOne(userId);
        if(user == null){
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        return ok(converter.convert(user));
    }
}
