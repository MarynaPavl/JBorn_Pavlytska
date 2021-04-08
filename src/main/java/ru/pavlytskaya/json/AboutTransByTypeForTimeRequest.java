package ru.pavlytskaya.json;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AboutTransByTypeForTimeRequest {
    private Long assignmentId;
    private String fromDate;
    private String toData;
}
