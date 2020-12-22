package ru.pavlytskaya.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class TransactionInformationDTO {
    private long id;
    private String transfer;
    private BigDecimal sum;
    private LocalDate data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "InformationDTO{" +
                "id=" + id +
                ", transfer='" + transfer + '\'' +
                ", sum=" + sum +
                ", data=" + data +
                '}' + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionInformationDTO that = (TransactionInformationDTO) o;
        return id == that.id &&
                Objects.equals(transfer, that.transfer) &&
                Objects.equals(sum, that.sum) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transfer, sum, data);
    }
}
