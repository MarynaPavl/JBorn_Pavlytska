package ru.pavlytskaya.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.pavlytskaya.service.TransactionInformationDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class AboutTransByTypeForTimeResponse {
    List<TransactionInformationDTO> information;
}
