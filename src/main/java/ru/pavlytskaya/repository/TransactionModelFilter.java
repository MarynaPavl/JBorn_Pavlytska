package ru.pavlytskaya.repository;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class TransactionModelFilter {
    private String assignmentLike;
    private LocalDate fromData;
    private LocalDate toData;
}
