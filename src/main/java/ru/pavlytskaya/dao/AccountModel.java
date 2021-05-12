package ru.pavlytskaya.dao;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Table(name = "account")
public class AccountModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name_account")
    private String nameAccount;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "currency")
    private String currency;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userModel;

    @OneToMany(mappedBy = "accountFrom")
    private Set<TransactionInformationModel> transactionsFrom;

    @OneToMany(mappedBy = "accountTo", orphanRemoval=true)
    private Set<TransactionInformationModel> transactionsTo;


    @Override
    public String toString() {
        return "AccountModel{" +
                "id=" + id +
                ", nameAccount='" + nameAccount + '\'' +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                '}';
    }
}
