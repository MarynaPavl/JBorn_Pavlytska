package ru.pavlytskaya;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.pavlytskaya.security.CustomUserDetailsService;

import static ru.pavlytskaya.security.UserRole.ADMIN;
import static ru.pavlytskaya.security.UserRole.USER;

@SpringBootApplication
public class WebApplication extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login-form").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("personal-area").hasAnyRole(USER.name(), ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/login-form")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/personal-area")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login-form");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
