package ru.pavlytskaya.service;

import org.springframework.stereotype.Service;
import ru.pavlytskaya.converter.Converter;
import ru.pavlytskaya.dao.TransactionToCategoryDao;
import ru.pavlytskaya.dao.TransactionToCategoryModel;

@Service
public class TransactionToCategoryService {
    private final TransactionToCategoryDao transactionToCategoryDao;
    private final Converter<TransactionToCategoryModel, TransactionToCategoryDTO> toCategoryDTOConverter;

    public TransactionToCategoryService(TransactionToCategoryDao transactionToCategoryDao, Converter<TransactionToCategoryModel, TransactionToCategoryDTO> toCategoryDTOConverter) {
        this.transactionToCategoryDao = transactionToCategoryDao;
        this.toCategoryDTOConverter = toCategoryDTOConverter;
    }
    public TransactionToCategoryDTO transactionToCategoryInsert(Long transactionId, Long typeId){
        TransactionToCategoryModel toCategoryModel = transactionToCategoryDao.insert(transactionId, typeId);
        if (toCategoryModel == null){
            return null;
        }
        return toCategoryDTOConverter.convert(toCategoryModel);
    }
}
