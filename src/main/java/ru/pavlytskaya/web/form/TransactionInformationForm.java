package ru.pavlytskaya.web.form;

import lombok.Data;

@Data
public class TransactionInformationForm {
    private String assignment;
    private String fromDate;
    private String toData;
}
