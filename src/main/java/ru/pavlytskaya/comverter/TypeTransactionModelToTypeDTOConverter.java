package ru.pavlytskaya.comverter;

import ru.pavlytskaya.dao.TypeTransactionModel;
import ru.pavlytskaya.service.TypeDTO;

public class TypeTransactionModelToTypeDTOConverter implements Converter<TypeTransactionModel, TypeDTO> {
    @Override
    public TypeDTO convert(TypeTransactionModel source) {
        TypeDTO typeDTO = new TypeDTO();
        typeDTO.setId(source.getId());
        typeDTO.setAssignment(source.getAssignment());
        return typeDTO;
    }
}
