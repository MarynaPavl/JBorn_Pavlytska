package ru.pavlytskaya.repository;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Data
@Accessors(chain = true)
@Component
@Service
public class TypeTransactionModelFilter {
    private String assignmentLike;

}
