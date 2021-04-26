package ru.pavlytskaya.dao;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "accountFrom", fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "accountFrom", fetch = FetchType.EAGER)
    private List<TransactionInformationModel> transactions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountModel that = (AccountModel) o;
        return id == that.id && Objects.equals(nameAccount, that.nameAccount) && Objects.equals(balance, that.balance) && Objects.equals(currency, that.currency) && Objects.equals(userModel, that.userModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameAccount, balance, currency, userModel);
    }

    @Override
    public String toString() {
        return "AccountModel{" +
                "id=" + id +
                ", nameAccount='" + nameAccount + '\'' +
                ", balance=" + balance +
                ", currency='" + currency + '\''  +
                '}';
    }
}
