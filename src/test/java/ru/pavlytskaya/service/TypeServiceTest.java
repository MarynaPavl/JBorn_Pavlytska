package ru.pavlytskaya.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.pavlytskaya.converter.TypeTransactionModelToTypeDTOConverter;
import ru.pavlytskaya.dao.TypeDao;
import ru.pavlytskaya.dao.TypeTransactionModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TypeServiceTest {
    @InjectMocks TypeService subj;
    @Mock TypeDao typeDao;
    @Mock TypeTransactionModelToTypeDTOConverter typeDTOConverter;

    @Test
    public void type_NotFound(){
      when(typeDao.typeInformation()).thenReturn(null);

      List<TypeDTO> list = subj.typeInformation();

      assertNull(list);

      verify(typeDao, times(1)).typeInformation();
      verifyNoMoreInteractions(typeDTOConverter);
    }

    @Test
    public void type_Found(){
        List<TypeTransactionModel> typeTransactionModels = new ArrayList<>();
        TypeTransactionModel typeTransactionModel = new TypeTransactionModel();
        typeTransactionModel.setId(1);
        typeTransactionModel.setAssignment("food");
        typeTransactionModels.add(typeTransactionModel);

        when(typeDao.typeInformation()).thenReturn(typeTransactionModels);

        List<TypeDTO> typeDTOList = new ArrayList<>();
        TypeDTO typeDTO = new TypeDTO();
        typeDTO.setId(1);
        typeDTO.setAssignment("food");
        typeDTOList.add(typeDTO);

        when(typeDTOConverter.convert(typeTransactionModels)).thenReturn(typeDTOList);

        List<TypeDTO> list = subj.typeInformation();

        assertNotNull(list);
        assertEquals(typeDTOList, list);

        verify(typeDao, times(1)).typeInformation();
        verify(typeDTOConverter, times(1)).convert(typeTransactionModels);
    }

    @Test
    public void type_NotCreated() {
        when(typeDao.creatType("food")).thenReturn(null);

        TypeDTO type = subj.typeCreat("food");

        assertNull(type);

        verify(typeDao, times(1)).creatType("food");
        verifyNoMoreInteractions(typeDTOConverter);
    }

    @Test
    public void type_CreatedSuccessful() {
        TypeTransactionModel transactionModel = new TypeTransactionModel();
        transactionModel.setId(1);
        transactionModel.setAssignment("food");
        when(typeDao.creatType("food")).thenReturn(transactionModel);
        TypeDTO typeDTO = new TypeDTO();
        typeDTO.setId(1);
        typeDTO.setAssignment("food");
        when(typeDTOConverter.convert(transactionModel)).thenReturn(typeDTO);

        TypeDTO type = subj.typeCreat("food");

        assertNotNull(type);
        assertEquals(typeDTO, type);

        verify(typeDao, times(1)).creatType("food");
        verify(typeDTOConverter, times(1)).convert(transactionModel);
    }

    @Test
    public void editType() {
        int name = subj.editType(1, "name");
        verify(typeDao, times(1)).editType(1, "name");
    }

    @Test
    public void deleteType() {
        int i = subj.deleteType(1);
        verify(typeDao, times(1)).delete(1);
    }
}