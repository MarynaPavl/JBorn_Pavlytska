package ru.pavlytskaya.service;

import java.util.Objects;

public class TransactionToCategoryDTO {
    private long idTransaction;
    private long idCategory;

    public long getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(long idTransaction) {
        this.idTransaction = idTransaction;
    }

    public long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(long idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public String toString() {
        return "TransactionToCategoryDTO{" +
                "idTransaction=" + idTransaction +
                ", idCategory=" + idCategory +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionToCategoryDTO that = (TransactionToCategoryDTO) o;
        return idTransaction == that.idTransaction &&
                idCategory == that.idCategory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransaction, idCategory);
    }
}
