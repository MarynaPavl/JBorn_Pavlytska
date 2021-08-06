package ru.pavlytskaya.service;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pavlytskaya.converter.Converter;
import ru.pavlytskaya.entity.AccountModel;
import ru.pavlytskaya.entity.TransactionInformationModel;
import ru.pavlytskaya.entity.TypeTransactionModel;
import ru.pavlytskaya.exception.CustomException;
import ru.pavlytskaya.repository.AccountModelRepository;
import ru.pavlytskaya.repository.TransactionModelRepository;
import ru.pavlytskaya.repository.TypeTransactionModelRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class TransactionInformationService {
    private final TransactionModelRepository transactionModelRepository;
    private final AccountModelRepository accountModelRepository;
    private final TypeTransactionModelRepository typeTransactionModelRepository;
    private final Converter<TransactionInformationModel, TransactionInformationDTO> informationDTOConverter;

    @Transactional
    public TransactionInformationDTO transactionInsert(Long accountFrom, Long accountTo, BigDecimal sum, @NotNull LocalDate data, Set<Long> typeId) {
        TransactionInformationModel informationModel = new TransactionInformationModel();
        if (accountFrom > 0) {
            AccountModel accountModelFrom = accountModelRepository.findAccountModelById(accountFrom);
            BigDecimal balanceFrom = accountModelFrom.getBalance();
            if (sum.compareTo(balanceFrom) > 0) {
                throw new CustomException("Transaction amount exceeds balance");
            }
            accountModelFrom.setBalance(balanceFrom.subtract(sum));
            informationModel.setAccountFrom(accountModelFrom);
        }


        if (accountTo > 0) {
            AccountModel accountModelTo = accountModelRepository.findAccountModelById(accountTo);
            BigDecimal balanceTo = accountModelTo.getBalance();
            accountModelTo.setBalance(balanceTo.add(sum));
            informationModel.setAccountTo(accountModelTo);
        }
        Iterator<Long> it = typeId.iterator();
        Set<TypeTransactionModel> types = new HashSet<>();
        while (it.hasNext()) {
            Long id = it.next();
            TypeTransactionModel typeTransactionModel = typeTransactionModelRepository.findTypeTransactionModelById(id);
            types.add(typeTransactionModel);
        }
        informationModel.setSum(sum)
                .setData(data)
                .setTypes(types);

        transactionModelRepository.save(informationModel);
        return informationDTOConverter.convert(informationModel);
    }

    public int deleteTransaction(long id) {
        try {
            transactionModelRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return e.getActualSize();
        }
        return 1;
    }

    public List<TransactionInformationDTO> informationModels(String assignment, LocalDate fromDate, LocalDate toData) {
        List<TypeTransactionModel> assignments = typeTransactionModelRepository.findAllByAssignmentIsStartingWith(assignment);

        if (assignments.size() > 0) {
            List<TransactionInformationModel> informationModels = new ArrayList<>();
            for (TypeTransactionModel type : assignments) {
                long assignmentId = type.getId();
                List<TransactionInformationModel> information = transactionModelRepository.findAllByTypesIdAndDataBetween(assignmentId, fromDate, toData);
                informationModels.addAll(information);
            }
            if (informationModels.size() == 0) {
                return null;
            }
            return informationDTOConverter.convert(informationModels);
        } else {
            return null;
        }
    }
}

