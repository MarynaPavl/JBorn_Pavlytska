package ru.pavlytskaya;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.pavlytskaya.security.CustomGrantedAuthority;
import ru.pavlytskaya.security.CustomUserDetails;

import static java.util.Collections.singleton;
import static ru.pavlytskaya.security.UserRole.USER;

@Configuration
public class MockSecurityConfiguration {
    @Bean
    public UserDetailsService userDetailsService(){
        return s -> new CustomUserDetails(
                1L,
                "Marina",
                "Pavlytskaya",
                "marinabushneva@gmail.com",
                "12345678",
                singleton(new CustomGrantedAuthority(USER))
        );
    }

}
