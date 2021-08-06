package ru.pavlytskaya.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.converter.Converter;
import ru.pavlytskaya.entity.TypeTransactionModel;
import ru.pavlytskaya.repository.TypeTransactionModelRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TypeService {
    private final TypeTransactionModelRepository typeRepository;
    private final Converter<TypeTransactionModel, TypeDTO> typeDTOConverter;


    public List<TypeDTO> typeInformation(String str) {
        List<TypeTransactionModel> typeTransactionModel = typeRepository.findAllByAssignmentIsStartingWith(str);
        if (typeTransactionModel == null) {
            return null;
        }
        return typeDTOConverter.convert(typeTransactionModel);
    }

    public TypeDTO typeCreat(String assignment) {

        TypeTransactionModel typeTransactionModel = new TypeTransactionModel()
                .setAssignment(assignment);
        if (typeTransactionModel == null) {
            return null;
        }
        typeRepository.save(typeTransactionModel);
        return typeDTOConverter.convert(typeTransactionModel);
    }

    public int delete(Long assignmentId) {
        try {
            typeRepository.deleteById(assignmentId);
        } catch (EmptyResultDataAccessException e) {
            return e.getActualSize();
        }
        return 1;
    }

}