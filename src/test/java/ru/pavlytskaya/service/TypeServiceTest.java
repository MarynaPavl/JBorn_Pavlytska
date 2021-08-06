package ru.pavlytskaya.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.pavlytskaya.converter.TypeTransactionModelToTypeDTOConverter;
import ru.pavlytskaya.entity.TypeTransactionModel;
import ru.pavlytskaya.repository.TypeTransactionModelRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.*;


@RunWith(MockitoJUnitRunner.class)
public class TypeServiceTest {
    @InjectMocks
    TypeService subj;
    @Mock
    TypeTransactionModelRepository typeModelRepository;
    @Mock
    TypeTransactionModelToTypeDTOConverter typeDTOConverter;

    @Test
    public void type_NotFound() {
        when(typeModelRepository.findAllByAssignmentIsStartingWith("t")).thenReturn(null);

        List<TypeDTO> list = subj.typeInformation("t");

        assertNull(list);

        verify(typeModelRepository, times(1)).findAllByAssignmentIsStartingWith("t");
        verifyNoMoreInteractions(typeDTOConverter);
    }

    @Test
    public void type_Found() {
        List<TypeTransactionModel> typeTransactionModels = new ArrayList<>();
        TypeTransactionModel typeTransactionModel = new TypeTransactionModel().setId(1).setAssignment("food");
        typeTransactionModels.add(typeTransactionModel);
        doReturn(typeTransactionModels).when(typeModelRepository).findAllByAssignmentIsStartingWith("food");

        List<TypeDTO> typeDTOList = new ArrayList<>();
        TypeDTO typeDTO = new TypeDTO().setId(1).setAssignment("food");
        typeDTOList.add(typeDTO);
        doReturn(typeDTOList).when(typeDTOConverter).convert(typeTransactionModels);

        List<TypeDTO> list = subj.typeInformation("food");

        assertNotNull(list);
        assertEquals(typeDTOList, list);

        verify(typeModelRepository, times(1)).findAllByAssignmentIsStartingWith("food");
        verify(typeDTOConverter, times(1)).convert(typeTransactionModels);
    }

    @Test
    public void type_NotCreated() {
        TypeTransactionModel type = new TypeTransactionModel();
        doReturn(null).when(typeModelRepository).save(type.setAssignment("food"));


        TypeDTO typeDTO = subj.typeCreat("food");

        assertNull(typeDTO);

        verify(typeModelRepository, times(1)).save(type.setAssignment("food"));
        verify(typeDTOConverter, times(1)).convert(type);
    }

    @Test
    public void type_CreatedSuccessful() {
        TypeTransactionModel transactionModel = new TypeTransactionModel().setAssignment("food");
        doReturn(transactionModel).when(typeModelRepository).save(transactionModel.setAssignment("food"));

        TypeDTO typeDTO = new TypeDTO().setId(1).setAssignment("food");
        when(typeDTOConverter.convert(transactionModel)).thenReturn(typeDTO);

        TypeDTO type = subj.typeCreat("food");

        assertNotNull(type);
        assertEquals(typeDTO, type);

        verify(typeModelRepository, times(1)).save(transactionModel.setAssignment("food"));
        verify(typeDTOConverter, times(1)).convert(transactionModel);
    }

    @Test
    public void deleteType() {
        doThrow(EmptyResultDataAccessException.class).when(typeModelRepository).deleteById(1L);
        subj.delete(1L);
        verify(typeModelRepository, times(1)).deleteById(1L);
    }

}