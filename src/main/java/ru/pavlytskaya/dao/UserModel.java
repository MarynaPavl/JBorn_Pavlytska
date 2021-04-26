package ru.pavlytskaya.dao;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "service_users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email_address")
    private String email;
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "userModel", fetch = FetchType.EAGER)
    private List<AccountModel> accounts;
}
