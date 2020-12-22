package ru.pavlytskaya.service;

import ru.pavlytskaya.converter.Converter;
import ru.pavlytskaya.dao.TransactionInformationDao;
import ru.pavlytskaya.dao.TransactionInformationModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TransactionInformationService {
    private final TransactionInformationDao transactionInformationDao;
    private final Converter<TransactionInformationModel, TransactionInformationDTO> informationDTOConverter;

    public TransactionInformationService(TransactionInformationDao transactionInformationDao, Converter<TransactionInformationModel, TransactionInformationDTO> informationDTOConverter) {
        this.transactionInformationDao = transactionInformationDao;
        this.informationDTOConverter = informationDTOConverter;
    }
    public TransactionInformationDTO transactionInsert(Integer accountFrom, Integer accountTo, BigDecimal sum, LocalDate data){
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
