package ru.pavlytskaya.json;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AboutTransByTypeForTimeRequest {
    private Long assignmentId;
    @NotNull
    private String fromDate;
    @NotNull
    private String toData;
}
