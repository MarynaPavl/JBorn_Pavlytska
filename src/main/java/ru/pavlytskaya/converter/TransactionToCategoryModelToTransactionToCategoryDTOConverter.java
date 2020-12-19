package ru.pavlytskaya.converter;

import ru.pavlytskaya.dao.TransactionToCategoryModel;
import ru.pavlytskaya.service.TransactionToCategoryDTO;

public class TransactionToCategoryModelToTransactionToCategoryDTOConverter implements Converter<TransactionToCategoryModel, TransactionToCategoryDTO>{
    @Override
    public TransactionToCategoryDTO convert(TransactionToCategoryModel source) {
        TransactionToCategoryDTO toCategoryDTO = new TransactionToCategoryDTO();
        toCategoryDTO.setIdTransaction(source.getIdTransaction());
        toCategoryDTO.setIdCategory(source.getIdCategory());
        return toCategoryDTO;
    }
}
