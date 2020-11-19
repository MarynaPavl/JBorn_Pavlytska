package ru.pavlytskaya.dao;

import java.util.Objects;

public class TypeTransactionModel {
    private long id;
    private String assignment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeTransactionModel that = (TypeTransactionModel) o;
        return id == that.id &&
                Objects.equals(assignment, that.assignment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, assignment);
    }
}
