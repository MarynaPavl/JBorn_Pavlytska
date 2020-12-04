package ru.pavlytskaya.service;

import java.util.Objects;

public class AccountDTO {
    private long id;
    private String nameAccount;
    private double balance;
    private String currency;


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

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    @Override
    public String toString() {
        return "AccountDTO {" +
                "id = " + id +
                ", nameAccount = '" + nameAccount + '\'' +
                ", balance = " + balance +
                ", currency = '" + currency + '\'' +
                "}" + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return id == that.id &&
                Double.compare(that.balance, balance) == 0 &&
                Objects.equals(nameAccount, that.nameAccount) &&
                Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameAccount, balance, currency);
    }
}
