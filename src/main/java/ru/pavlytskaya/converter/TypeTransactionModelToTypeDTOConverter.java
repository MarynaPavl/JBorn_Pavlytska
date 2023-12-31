package ru.pavlytskaya.converter;

import org.springframework.stereotype.Service;
import ru.pavlytskaya.entity.TypeTransactionModel;
import ru.pavlytskaya.service.TypeDTO;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class TypeTransactionModelToTypeDTOConverter implements Converter<TypeTransactionModel, TypeDTO> {
    @Override
    public TypeDTO convert(TypeTransactionModel source) {
        TypeDTO typeDTO = new TypeDTO();
        typeDTO.setId(source.getId());
        typeDTO.setAssignment(source.getAssignment());
        return typeDTO;
    }

    public List<TypeDTO> convert(List<TypeTransactionModel> source) {
        return source.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
