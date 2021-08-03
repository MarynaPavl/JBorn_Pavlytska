package ru.pavlytskaya.api.json;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AboutTransByTypeForTimeRequest {
    @NotNull
    private String assignment;
    @NotNull
    private String fromDate;
    @NotNull
    private String toData;
}
