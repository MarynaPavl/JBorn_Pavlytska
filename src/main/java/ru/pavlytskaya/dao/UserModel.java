package ru.pavlytskaya.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "service_users")
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "User.find", query = "select u from UserModel u where u.email =:email and u.password =:hash")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email_address", unique = true, nullable = false)
    private String email;
    @Column(name = "password")
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<AccountModel> accounts;

}
