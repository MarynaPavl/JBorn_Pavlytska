package ru.pavlytskaya.dao;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

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

    @OneToOne(mappedBy = "accountFrom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TransactionInformationModel transactionsFrom;

    @OneToOne(mappedBy = "accountTo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TransactionInformationModel transactionsTo;



    public AccountModel() {
    }

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
