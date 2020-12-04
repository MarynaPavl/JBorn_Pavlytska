package ru.pavlytskaya.service;

import ru.pavlytskaya.converter.Converter;
import ru.pavlytskaya.dao.TypeDao;
import ru.pavlytskaya.dao.TypeTransactionModel;

import java.util.List;

public class TypeService {
    private final TypeDao typeDao;
    private final Converter<TypeTransactionModel, TypeDTO> typeDTOConverter;

    public TypeService(TypeDao typeDao, Converter<TypeTransactionModel, TypeDTO> typeDTOConverter) {
        this.typeDao = typeDao;
        this.typeDTOConverter = typeDTOConverter;
    }

    public List<TypeDTO> typeInformation() {
        List<TypeTransactionModel> typeTransactionModel = typeDao.typeInformation();
        if (typeTransactionModel == null) {
            return null;
        }
        return typeDTOConverter.convert(typeTransactionModel);
    }

    public TypeDTO typeCreat(String assignment) {
        TypeTransactionModel typeTransactionModel = typeDao.creatType(assignment);
        if (typeTransactionModel == null) {
            return null;
        }
        return typeDTOConverter.convert(typeTransactionModel);
    }

    public int editType(long id, String assignment) {

        return typeDao.editType(id, assignment);
    }

    public int deleteType(long id) {
        return typeDao.delete(id);
    }
}