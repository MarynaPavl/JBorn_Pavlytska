package ru.pavlytskaya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pavlytskaya.dao.UserModel;

public interface UserModelRepository extends JpaRepository<UserModel, Long> {

}
