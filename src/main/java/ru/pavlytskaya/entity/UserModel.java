package ru.pavlytskaya.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.pavlytskaya.security.UserRole;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptySet;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "service_users")
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "User.find", query = "select u from UserModel u where u.email =:email and u.password =:hash")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @JsonManagedReference
    private List<AccountModel> accounts;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "service_user_id"))
    @Column(name = "role")
    private Set<UserRole> roles = emptySet();

}
