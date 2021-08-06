package ru.pavlytskaya.api.apiConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.pavlytskaya.api.json.TypeCreatResponse;
import ru.pavlytskaya.api.json.TypeInformationResponse;
import ru.pavlytskaya.service.TypeDTO;

import java.util.List;

@Component
public class TypeTransactionModelToResponseConverter implements Converter<TypeDTO, TypeCreatResponse> {
    @Override
    public TypeCreatResponse convert(TypeDTO typeDTO) {
        return new TypeCreatResponse(typeDTO.getId(), typeDTO.getAssignment());
    }

    public TypeInformationResponse convert(List<TypeDTO> types) {
        return new TypeInformationResponse(types);
    }
}
