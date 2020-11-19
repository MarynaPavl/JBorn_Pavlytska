package ru.pavlytskaya.comverter;

import ru.pavlytskaya.dao.TransactionInformationModel;
import ru.pavlytskaya.service.InformationDTO;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionInformationModelToInformationDTOConverter implements Converter<TransactionInformationModel, InformationDTO> {
    @Override
    public InformationDTO convert(TransactionInformationModel source) {
        InformationDTO informationDTO = new InformationDTO();
        String tr = null;
        informationDTO.setId(source.getId());
        if (source.getAccount_to() == 0) {
            tr = "expense";
        }
        if (source.getAccount_from() == 0) {
            tr = "income";
        }
        if (source.getAccount_from() != 0 & source.getAccount_to() != 0) {
            tr = "transfer between accounts";
        }
        informationDTO.setTransfer(tr);
        informationDTO.setSum(source.getSum());
        informationDTO.setData(source.getData());

        return informationDTO;
    }

    public List<InformationDTO> convert(List<TransactionInformationModel> sourse) {
        return sourse.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
