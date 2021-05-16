package ru.pavlytskaya.dao;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "transaction")
public class TransactionInformationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account_from")
    private AccountModel accountFrom;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account_to")
    private AccountModel accountTo;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "time")
    private LocalDate data;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "transaction_to_category",
            joinColumns = @JoinColumn(name = "transaction_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private List<TypeTransactionModel> types;


}
