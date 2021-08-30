package ru.pavlytskaya.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.entity.UserModel;
import ru.pavlytskaya.repository.UserModelRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserModelRepository userModelRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userModelRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Can't find user with email " + email);
        }
        return new CustomUserDetails(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(CustomGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
    }
}
