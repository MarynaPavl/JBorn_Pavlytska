package ru.pavlytskaya.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.json.TransactionCreatRequest;
import ru.pavlytskaya.json.TransactionCreateResponse;
import ru.pavlytskaya.service.TransactionInformationDTO;
import ru.pavlytskaya.service.TransactionInformationService;

import java.time.LocalDate;

@Service("/transactionCreate")
@RequiredArgsConstructor
public class TransactionCreateController implements SecureController<TransactionCreatRequest, TransactionCreateResponse> {
    private final TransactionInformationService transactionInformationService;

    @Override
    public TransactionCreateResponse handler(TransactionCreatRequest request, Long userId) {
        TransactionInformationDTO transactionInformationDTO = transactionInformationService.transactionInsert(
                request.getAccountFrom(), request.getAccountTo(), request.getSum(), LocalDate.parse(request.getData()));
        if (transactionInformationDTO != null) {
            return new TransactionCreateResponse(
                    transactionInformationDTO.getId(), transactionInformationDTO.getTransfer(),
                    transactionInformationDTO.getSum(), transactionInformationDTO.getData().toString());
        }
        return null;
    }

    @Override
    public Class<TransactionCreatRequest> getRequestClass() {
        return TransactionCreatRequest.class;
    }
}
