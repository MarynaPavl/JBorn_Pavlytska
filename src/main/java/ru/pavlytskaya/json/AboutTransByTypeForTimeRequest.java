package ru.pavlytskaya.json;

import lombok.Data;

@Data
public class AboutTransByTypeForTimeRequest {
    private Long assignmentId;
    private String fromDate;
    private String toData;
}
