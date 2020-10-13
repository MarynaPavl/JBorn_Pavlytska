package ru.pavlytskaya.service;

public class AccountDTO {
    private long id;
    private String nameAccount;
    private double balance;
    private String currency;
    private long userID;


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

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", nameAccount='" + nameAccount + '\'' +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", userID=" + userID +
                '}';
    }
}
