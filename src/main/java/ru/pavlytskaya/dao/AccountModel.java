package ru.pavlytskaya.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "Account.listAccount", query = "select a from AccountModel a where a.user.id =:userID")

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
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserModel user;

    @OneToMany(mappedBy = "accountFrom")
    private List<TransactionInformationModel> transactionsFrom;

    @OneToMany(mappedBy = "accountTo")
    private List<TransactionInformationModel> transactionsTo;


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
