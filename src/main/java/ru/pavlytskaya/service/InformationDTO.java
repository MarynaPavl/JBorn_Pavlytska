package ru.pavlytskaya.service;

import java.time.LocalDate;
import java.util.Objects;

public class InformationDTO {
    private long id;
    private String transfer;
    private double sum;
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
        InformationDTO that = (InformationDTO) o;
        return id == that.id &&
                Double.compare(that.sum, sum) == 0 &&
                Objects.equals(transfer, that.transfer) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transfer, sum, data);
    }
}
