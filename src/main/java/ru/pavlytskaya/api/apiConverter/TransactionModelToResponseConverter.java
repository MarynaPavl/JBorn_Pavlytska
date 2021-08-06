package ru.pavlytskaya.api.apiConverter;

import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;
import ru.pavlytskaya.api.json.AboutTransByTypeForTimeResponse;
import ru.pavlytskaya.api.json.TransactionCreateResponse;
import ru.pavlytskaya.service.TransactionInformationDTO;

import java.util.List;

@Component
public class TransactionModelToResponseConverter implements Converter<TransactionInformationDTO, TransactionCreateResponse> {
    @Override
    public TransactionCreateResponse convert(TransactionInformationDTO source) {
        return new TransactionCreateResponse(source.getId(), source.getTransfer(), source.getSum(), source.getData());
    }

    public AboutTransByTypeForTimeResponse convert(List<TransactionInformationDTO> transactions) {
        return new AboutTransByTypeForTimeResponse(transactions);
    }
}
