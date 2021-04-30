package ru.pavlytskaya.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class UserDao {
    @PersistenceContext
    private EntityManager em;


    @Transactional
    public UserModel findByEmailAndHash(String email, String hash) {

        return em.createQuery("select u from UserModel u where u.email =:email and u.password =:hash", UserModel.class)
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

    public UserModel findById(Long userId) {
        UserModel userModel = null;
//        try (Connection conn = dataSource.getConnection()) {
//            PreparedStatement ps = conn.prepareStatement("select *from service_users where id = ?");
//            ps.setLong(1, userId);
//
//
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                userModel = createUserModelByResultSet(rs);
//            }
//        } catch (SQLException e) {
//            throw new CustomException(e);
//        }

        return userModel;
    }

    private UserModel createUserModelByResultSet(ResultSet rs) throws SQLException {
        UserModel userModel = new UserModel();
        userModel.setId(rs.getLong("id"));
        userModel.setFirstName(rs.getString("first_name"));
        userModel.setLastName(rs.getString("last_name"));
        userModel.setEmail(rs.getString("email_address"));
        userModel.setPassword(rs.getString("password"));
        return userModel;
    }
}
