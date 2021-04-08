package ru.pavlytskaya.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.json.TransactionDeleteRequest;
import ru.pavlytskaya.json.TransactionDeleteResponse;
import ru.pavlytskaya.service.TransactionInformationService;

@Service("/transactionDelete")
@RequiredArgsConstructor
public class TransactionDeleteController implements SecureController<TransactionDeleteRequest, TransactionDeleteResponse> {
    private final TransactionInformationService transactionInformationService;

    @Override
    public TransactionDeleteResponse handler(TransactionDeleteRequest request, Long userId) {
        int row = transactionInformationService.deleteTransaction(request.getTransactionId());
        if (row == 1) {
            return new TransactionDeleteResponse("Operation was successfully completed.");
        }
        if (row == 0) {
            return new TransactionDeleteResponse("Mistake!");
        }
        return null;
    }

    @Override
    public Class<TransactionDeleteRequest> getRequestClass() {
        return TransactionDeleteRequest.class;
    }
}
