package ru.pavlytskaya.dao;

import java.time.LocalDate;
import java.util.Objects;

public class TransactionInformationModel {
    private long id;
    private Integer account_from;
    private Integer account_to;
    private double sum;
    private LocalDate data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getAccount_from() {
        return account_from;
    }

    public void setAccount_from(Integer account_from) {
        this.account_from = account_from;
    }

    public Integer getAccount_to() {
        return account_to;
    }

    public void setAccount_to(Integer account_to) {
        this.account_to = account_to;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionInformationModel that = (TransactionInformationModel) o;
        return id == that.id &&
                Double.compare(that.sum, sum) == 0 &&
                Objects.equals(account_from, that.account_from) &&
                Objects.equals(account_to, that.account_to) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account_from, account_to, sum, data);
    }
}
