package ru.pavlytskaya.converter;

import org.springframework.stereotype.Service;
import ru.pavlytskaya.dao.TransactionToCategoryModel;
import ru.pavlytskaya.service.TransactionToCategoryDTO;
@Service
public class TransactionToCategoryModelToTransactionToCategoryDTOConverter implements Converter<TransactionToCategoryModel, TransactionToCategoryDTO>{
    @Override
    public TransactionToCategoryDTO convert(TransactionToCategoryModel source) {
        TransactionToCategoryDTO toCategoryDTO = new TransactionToCategoryDTO();
        toCategoryDTO.setIdTransaction(source.getIdTransaction());
        toCategoryDTO.setIdCategory(source.getIdCategory());
        return toCategoryDTO;
    }
}
