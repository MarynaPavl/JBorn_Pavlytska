package ru.pavlytskaya.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "Transaction.InformationList", query = "select t from TransactionInformationModel t join t.types a where a.id=:id and t.data>:fromData and t.data <:toData")
public class TransactionInformationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account_from")
    @JsonBackReference
    private AccountModel accountFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account_to")
    @JsonBackReference
    private AccountModel accountTo;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "time")
    private LocalDate data;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "transaction_to_category",
            joinColumns = @JoinColumn(name = "transaction_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<TypeTransactionModel> types = new HashSet<>();

    @Override
    public String toString() {
        return "TransactionInformationModel{" +
                "id=" + id +
                ", sum=" + sum +
                ", data=" + data +
                ", types=" + types +
                '}';
    }
}
