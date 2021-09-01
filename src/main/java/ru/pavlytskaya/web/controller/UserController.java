package ru.pavlytskaya.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.pavlytskaya.entity.UserModel;
import ru.pavlytskaya.repository.UserModelRepository;
import ru.pavlytskaya.security.CustomUserDetails;


@RequiredArgsConstructor
public abstract class UserController {
    private final UserModelRepository userModelRepository;



    public UserModel currentUser(){
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userModelRepository.findUserById(user.getId());
    }
}
