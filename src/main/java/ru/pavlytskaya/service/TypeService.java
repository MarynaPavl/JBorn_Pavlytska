package ru.pavlytskaya.service;

import ru.pavlytskaya.comverter.TypeTransactionModelToTypeDTOConverter;
import ru.pavlytskaya.dao.TypeDao;
import ru.pavlytskaya.dao.TypeTransactionModel;

public class TypeService {
    private final TypeDao typeDao;
    private final TypeTransactionModelToTypeDTOConverter typeDTOConverter;

    public TypeService() {
        this.typeDao = new TypeDao();
        this.typeDTOConverter = new TypeTransactionModelToTypeDTOConverter();
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

    public int deleteAccount(long id) {
        return typeDao.delete(id);
    }
}