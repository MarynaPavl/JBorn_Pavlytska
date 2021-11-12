package ru.pavlytskaya.repository;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@Component
public class TransactionModelFilter {
    private String assignmentLike;
    private LocalDate fromData;
    private LocalDate toData;

}
