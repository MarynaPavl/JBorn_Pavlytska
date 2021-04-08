package ru.pavlytskaya.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.pavlytskaya.service.AccountDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountInformationResponse {
    List<AccountDTO> accountDTO;
}
