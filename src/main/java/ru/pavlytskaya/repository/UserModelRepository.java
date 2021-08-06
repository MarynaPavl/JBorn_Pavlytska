package ru.pavlytskaya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.entity.UserModel;

@Service
@Component
public interface UserModelRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmailAndPassword(String email, String password);

    UserModel findUserById(long userID);

    Object findByEmail(String email);
}