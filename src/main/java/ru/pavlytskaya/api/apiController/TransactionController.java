package ru.pavlytskaya.api.apiController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pavlytskaya.api.json.*;
import ru.pavlytskaya.service.TransactionInformationDTO;
import ru.pavlytskaya.service.TransactionInformationService;
import ru.pavlytskaya.api.apiConverter.TransactionModelToResponseConverter;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TransactionController {
    private final TransactionInformationService transactionService;
    private final TransactionModelToResponseConverter converter;

    @GetMapping("/get-transaction-info")
    public @ResponseBody
    ResponseEntity<AboutTransByTypeForTimeResponse> transactionInformation(@RequestBody @Valid AboutTransByTypeForTimeRequest request) {
        List<TransactionInformationDTO> transactionInformation = transactionService.informationModels(
                request.getAssignment(),
                LocalDate.parse(request.getFromDate()),
                LocalDate.parse(request.getToData()));
        if (transactionInformation == null) {
            return status(HttpStatus.NOT_FOUND).build();
        }
        return ok(converter.convert(transactionInformation));
    }

    @PostMapping("/transaction-create")
    public @ResponseBody
    ResponseEntity<TransactionCreateResponse> createTransaction(@RequestBody @Valid TransactionCreatRequest request) {

        TransactionInformationDTO transactionDTO = transactionService.transactionInsert(
                request.getAccountFrom(),
                request.getAccountTo(),
                request.getSum(),
                request.getData(),
                request.getAssignmentId());
        if (transactionDTO == null) {
            return status(HttpStatus.BAD_REQUEST).build();
        }
        return ok(converter.convert(transactionDTO));
    }

    @DeleteMapping("/transaction-delete")
    public @ResponseBody
    ResponseEntity<String> deleteTransaction(@RequestBody @Valid TransactionDeleteRequest request) {
        int deleteTransaction = transactionService.deleteTransaction(request.getTransactionId());
        if (deleteTransaction == 0) {
            return status(HttpStatus.NOT_FOUND).build();
        }
        return ok("successfully");
    }
}
