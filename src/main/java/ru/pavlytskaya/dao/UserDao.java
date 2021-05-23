package ru.pavlytskaya.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserDao {
    @PersistenceContext
    private EntityManager em;


    @Transactional
    public UserModel findByEmailAndHash(String email, String hash) {

        return em.createNamedQuery("User.find", UserModel.class)
                .setParameter("email", email)
                .setParameter("hash", hash)
                .getSingleResult();
    }

    @Transactional
    public UserModel insert(String firstName, String lastName, String email, String hash) {

        UserModel userModel = new UserModel();
        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setEmail(email);
        userModel.setPassword(hash);

        em.persist(userModel);

        return userModel;
    }

}
