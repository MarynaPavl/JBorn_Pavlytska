package ru.pavlytskaya.service;

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
}
