package ru.pavlytskaya.api.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.pavlytskaya.service.AccountDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountInformationResponse {
    private List<AccountDTO> accountDTO;
}
