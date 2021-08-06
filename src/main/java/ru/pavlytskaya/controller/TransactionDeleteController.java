package ru.pavlytskaya.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.api.json.TransactionDeleteRequest;
import ru.pavlytskaya.api.json.TransactionDeleteResponse;
import ru.pavlytskaya.service.TransactionInformationService;

@Service("/transactionDelete")
@RequiredArgsConstructor
public class TransactionDeleteController implements SecureController<TransactionDeleteRequest, TransactionDeleteResponse> {
    private final TransactionInformationService transactionInformationService;

    @Override
    public TransactionDeleteResponse handler(TransactionDeleteRequest request, Long userId) {
        transactionInformationService.deleteTransaction(request.getTransactionId());
        return null;
    }

    @Override
    public Class<TransactionDeleteRequest> getRequestClass() {
        return TransactionDeleteRequest.class;
    }
}
