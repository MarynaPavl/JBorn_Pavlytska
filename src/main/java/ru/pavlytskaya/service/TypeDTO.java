package ru.pavlytskaya.service;

import java.util.Objects;

public class TypeDTO {
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
    public String toString() {
        return "TypeDTO{" +
                "id=" + id +
                ", assignment='" + assignment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeDTO typeDTO = (TypeDTO) o;
        return id == typeDTO.id &&
                Objects.equals(assignment, typeDTO.assignment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, assignment);
    }
}
