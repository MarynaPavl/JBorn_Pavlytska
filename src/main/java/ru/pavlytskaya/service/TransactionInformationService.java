package ru.pavlytskaya.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.converter.Converter;
import ru.pavlytskaya.dao.TransactionInformationDao;
import ru.pavlytskaya.dao.TransactionInformationModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionInformationService {
    private final TransactionInformationDao transactionInformationDao;
    private final Converter<TransactionInformationModel, TransactionInformationDTO> informationDTOConverter;

    public TransactionInformationDTO transactionInsert(Long accountFrom, Long accountTo, BigDecimal sum, LocalDate data){
        TransactionInformationModel informationModel = transactionInformationDao.insert(accountFrom, accountTo, sum, data);
        if (informationModel == null){
            return null;
        }
        return informationDTOConverter.convert(informationModel);
    }

    public int deleteTransaction(long id) {
        return transactionInformationDao.delete(id);
    }

    public List<TransactionInformationDTO> informationModels(long assignmentId, LocalDate fromDate, LocalDate toData) {
        List<TransactionInformationModel> informationModels = transactionInformationDao.informationModelList(assignmentId, fromDate, toData);
        if (informationModels == null) {
            return null;
        }
        return informationDTOConverter.convert(informationModels);
    }
}
