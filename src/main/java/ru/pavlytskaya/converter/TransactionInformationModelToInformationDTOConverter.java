package ru.pavlytskaya.converter;

import org.springframework.stereotype.Service;
import ru.pavlytskaya.entity.TransactionInformationModel;
import ru.pavlytskaya.service.TransactionInformationDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionInformationModelToInformationDTOConverter implements Converter<TransactionInformationModel, TransactionInformationDTO> {
    @Override
    public TransactionInformationDTO convert(TransactionInformationModel source) {
        TransactionInformationDTO transactionInformationDTO = new TransactionInformationDTO();
        String tr = null;
        transactionInformationDTO.setId(source.getId());
        if (source.getAccountTo() == null) {
            tr = "expense";
        }
        if (source.getAccountFrom() == null) {
            tr = "income";
        }
        if (source.getAccountFrom() != null & source.getAccountTo() != null) {
            tr = "transfer between accounts";
        }
        transactionInformationDTO.setTransfer(tr);
        transactionInformationDTO.setSum(source.getSum());
        transactionInformationDTO.setData(source.getData());

        return transactionInformationDTO;
    }

    public List<TransactionInformationDTO> convert(List<TransactionInformationModel> sourse) {
        return sourse.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
