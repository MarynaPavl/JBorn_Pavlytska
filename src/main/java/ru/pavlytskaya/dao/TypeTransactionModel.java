package ru.pavlytskaya.dao;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
@NamedQuery(name = "Type.List", query = "select t from TypeTransactionModel t")
public class TypeTransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String assignment;

}
