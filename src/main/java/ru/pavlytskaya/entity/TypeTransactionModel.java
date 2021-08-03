package ru.pavlytskaya.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "category")
@NamedQuery(name = "Type.List", query = "select t from TypeTransactionModel t")
public class TypeTransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String assignment;

}
