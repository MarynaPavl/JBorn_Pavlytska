package ru.pavlytskaya.web.form;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class TransactionInformationForm {
    private String assignment;
    private String fromDate;
    private String toData;
}
