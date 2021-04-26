package ru.pavlytskaya.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.pavlytskaya.converter.TransactionInformationModelToInformationDTOConverter;
import ru.pavlytskaya.dao.TransactionInformationDao;
import ru.pavlytskaya.dao.TransactionInformationModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionInformationServiceTest {
    @InjectMocks
    TransactionInformationService subj;
    @Mock
    TransactionInformationDao transactionInformationDao;
    @Mock
    TransactionInformationModelToInformationDTOConverter informationDTOConverter;

    @Test
    public void transaction_NotInsert() {
        when(transactionInformationDao.insert(1, 2, BigDecimal.valueOf(2000),
                LocalDate.of(2020, 12, 7))).thenReturn(null);
        TransactionInformationDTO transactionInsert = subj.transactionInsert(1, 2, BigDecimal.valueOf(2000),
                LocalDate.of(2020, 12, 7));
        assertNull(transactionInsert);

        verify(transactionInformationDao, times(1)).insert(1, 2, BigDecimal.valueOf(2000),
                LocalDate.of(2020, 12, 7));
        verifyNoMoreInteractions(informationDTOConverter);
    }

    @Test
    public void transaction_Insert_Successful() {
        TransactionInformationModel model = new TransactionInformationModel();
        model.setId(1);
        model.setAccountFrom(1);
        model.setAccountTo(2);
        model.setSum(BigDecimal.valueOf(2000));
        model.setData(LocalDate.of(2020, 12, 7));
        when(transactionInformationDao.insert(1, 2, BigDecimal.valueOf(2000),
                LocalDate.of(2020, 12, 7))).thenReturn(model);

        TransactionInformationDTO informationDTO = new TransactionInformationDTO();
        informationDTO.setId(1);
        informationDTO.setTransfer("transfer between accounts");
        informationDTO.setSum(BigDecimal.valueOf(2000));
        informationDTO.setData(LocalDate.of(2020, 12, 7).toString());
        when(informationDTOConverter.convert(model)).thenReturn(informationDTO);

        TransactionInformationDTO transactionInsert = subj.transactionInsert(1, 2, BigDecimal.valueOf(2000),
                LocalDate.of(2020, 12, 7));

        assertNotNull(transactionInsert);

        verify(transactionInformationDao, times(1)).insert(1, 2, BigDecimal.valueOf(2000),
                LocalDate.of(2020, 12, 7));
        verify(informationDTOConverter, times(1)).convert(model);
    }

    @Test
    public void deleteTransaction() {
        int i = subj.deleteTransaction(1);
        verify(transactionInformationDao, times(1)).delete(1);
    }

    @Test
    public void informationModels_NotFound() {
        when(transactionInformationDao.informationModelList(1, LocalDate.of(2020, 11, 13),
                LocalDate.of(2020, 11, 19))).thenReturn(null);
        List<TransactionInformationDTO> list = subj.informationModels(1, LocalDate.of(2020, 11, 13),
                LocalDate.of(2020, 11, 19));

        assertNull(list);

        verify(transactionInformationDao, times(1)).informationModelList(1, LocalDate.of(2020, 11, 13),
                LocalDate.of(2020, 11, 19));
        verifyNoMoreInteractions(informationDTOConverter);
    }

    @Test
    public void informationModels_Found() {
        List<TransactionInformationModel> informationModelList = new ArrayList<>();
        TransactionInformationModel model = new TransactionInformationModel();
        model.setId(1);
        model.setAccountFrom(1);
        model.setAccountTo(null);
        model.setSum(BigDecimal.valueOf(1.1));
        model.setData(LocalDate.of(2020, 11, 15));
        informationModelList.add(model);
        when(transactionInformationDao.informationModelList(1, LocalDate.of(2020, 11, 13),
                LocalDate.of(2020, 11, 19))).thenReturn(informationModelList);
        List<TransactionInformationDTO> transactionInformationDTOList = new ArrayList<>();
        TransactionInformationDTO transactionInformationDTO = new TransactionInformationDTO();
        transactionInformationDTO.setId(1);
        transactionInformationDTO.setTransfer("expense");
        transactionInformationDTO.setSum(BigDecimal.valueOf(1.1));
        transactionInformationDTO.setData(LocalDate.of(2020, 11, 15).toString());
        transactionInformationDTOList.add(transactionInformationDTO);
        when(informationDTOConverter.convert(informationModelList)).thenReturn(transactionInformationDTOList);

        List<TransactionInformationDTO> list = subj.informationModels(1, LocalDate.of(2020, 11, 13),
                LocalDate.of(2020, 11, 19));

        assertNotNull(list);
        assertEquals(transactionInformationDTOList, list);

        verify(transactionInformationDao, times(1)).informationModelList(1, LocalDate.of(2020, 11, 13),
                LocalDate.of(2020, 11, 19));
        verify(informationDTOConverter, times(1)).convert(informationModelList);
    }
}