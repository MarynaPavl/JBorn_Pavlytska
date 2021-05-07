//package ru.pavlytskaya.service;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import ru.pavlytskaya.converter.TransactionToCategoryModelToTransactionToCategoryDTOConverter;
//import ru.pavlytskaya.dao.TransactionToCategoryDao;
//import ru.pavlytskaya.dao.TransactionToCategoryModel;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class TransactionToCategoryServiceTest {
//   @InjectMocks TransactionToCategoryService subj;
//   @Mock TransactionToCategoryDao transactionToCategoryDao;
//   @Mock TransactionToCategoryModelToTransactionToCategoryDTOConverter converter;
//
//
//    @Test
//    public void transactionToCategoryInsert_NotCreated() {
//      when(transactionToCategoryDao.insert(1L, 2L)).thenReturn(null);
//
//        TransactionToCategoryDTO toCategoryDTO = subj.transactionToCategoryInsert(1L, 2L);
//
//        assertNull(toCategoryDTO);
//        verify(transactionToCategoryDao, times(1)).insert(1L, 2L);
//        verifyNoMoreInteractions(converter);
//    }
//
//    @Test
//    public void transactionToCategoryInsert_Created(){
//        TransactionToCategoryModel toCategoryModel = new TransactionToCategoryModel();
//        toCategoryModel.setIdTransaction(1);
//        toCategoryModel.setIdCategory(2);
//        when(transactionToCategoryDao.insert(1L,2L)).thenReturn(toCategoryModel);
//        TransactionToCategoryDTO toCategoryDTO = new TransactionToCategoryDTO();
//        toCategoryDTO.setIdTransaction(1);
//        toCategoryDTO.setIdCategory(2);
//        when(converter.convert(toCategoryModel)).thenReturn(toCategoryDTO);
//
//        TransactionToCategoryDTO categoryDTO = subj.transactionToCategoryInsert(1L, 2L);
//
//        assertNotNull(categoryDTO);
//        assertEquals(toCategoryDTO, categoryDTO);
//
//        verify(transactionToCategoryDao, times(1)).insert(1L,2L);
//        verify(converter, times(1)).convert(toCategoryModel);
//    }
//}