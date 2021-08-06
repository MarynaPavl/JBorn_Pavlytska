package ru.pavlytskaya.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.pavlytskaya.converter.TransactionInformationModelToInformationDTOConverter;
import ru.pavlytskaya.entity.AccountModel;
import ru.pavlytskaya.entity.TransactionInformationModel;
import ru.pavlytskaya.entity.TypeTransactionModel;
import ru.pavlytskaya.repository.AccountModelRepository;
import ru.pavlytskaya.repository.TransactionModelRepository;
import ru.pavlytskaya.repository.TypeTransactionModelRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class TransactionInformationServiceTest {
    @InjectMocks
    TransactionInformationService subj;
    @Mock
    TransactionModelRepository transactionModelRepository;
    @Mock
    AccountModelRepository accountModelRepository;
    @Mock
    TypeTransactionModelRepository typeModelRepository;
    @Mock
    TransactionInformationModelToInformationDTOConverter informationDTOConverter;

    @Test
    public void transaction_NotInsert() {
        TransactionInformationModel transactionModel = new TransactionInformationModel();
        AccountModel accountModel = accountModelRepository.findAccountModelById(0L);
        Set<TypeTransactionModel> types = new HashSet<>();
        TypeTransactionModel typeModelById = typeModelRepository.findTypeTransactionModelById(1L);
        types.add(typeModelById);
        doReturn(null).when(transactionModelRepository).save(transactionModel.setAccountTo(accountModel)
                .setSum(BigDecimal.valueOf(2000)).setData(LocalDate.of(2020, 12, 7)).setTypes(types));

        TransactionInformationDTO transactionInsert = subj.transactionInsert(0L, 0L, BigDecimal.valueOf(2000),
                LocalDate.of(2020, 12, 7), Collections.singleton(1L));

        assertNull(transactionInsert);

        verify(accountModelRepository, times(1)).findAccountModelById(0L);
        verify(typeModelRepository, times(2)).findTypeTransactionModelById(1L);
        verify(transactionModelRepository, times(1)).save(transactionModel);
        verify(informationDTOConverter, times(1)).convert(transactionModel);
    }

    @Test
    public void transaction_Insert_Successful() {
        TransactionInformationModel transactionModel = new TransactionInformationModel();
        AccountModel accountModel = accountModelRepository.findAccountModelById(0L);
        Set<TypeTransactionModel> types = new HashSet<>();
        TypeTransactionModel typeModelById = typeModelRepository.findTypeTransactionModelById(1L);
        types.add(typeModelById);

        doReturn(transactionModel).when(transactionModelRepository).save(transactionModel.setAccountTo(accountModel)
                .setSum(BigDecimal.valueOf(2000)).setData(LocalDate.of(2020, 12, 7)).setTypes(types));

        TransactionInformationDTO transactionDTO = new TransactionInformationDTO().setId(1).setTransfer("income")
                .setSum(BigDecimal.valueOf(2000)).setData(LocalDate.of(2020, 12, 7));
        doReturn(transactionDTO).when(informationDTOConverter).convert(transactionModel);

        TransactionInformationDTO transaction = subj.transactionInsert(0L, 0L, BigDecimal.valueOf(2000),
                LocalDate.of(2020, 12, 7), Collections.singleton(1L));

        assertNotNull(transaction);
        assertEquals(transactionDTO, transaction);

        verify(accountModelRepository, times(1)).findAccountModelById(0L);
        verify(typeModelRepository, times(2)).findTypeTransactionModelById(1L);
        verify(transactionModelRepository, times(1)).save(transactionModel);
        verify(informationDTOConverter, times(1)).convert(transactionModel);
    }

    @Test
    public void deleteTransaction() {
        doThrow(EmptyResultDataAccessException.class).when(transactionModelRepository).deleteById(1L);
        subj.deleteTransaction(1);
        verify(transactionModelRepository, times(1)).deleteById(1L);
    }

    @Test
    public void informationModels_NotFound() {
        List<TransactionInformationDTO> list = subj.informationModels("travels", LocalDate.of(2020, 11, 13),
                LocalDate.of(2020, 11, 19));

        assertNull(list);

        verify(typeModelRepository, times(1)).findAllByAssignmentIsStartingWith("travels");
        verify(transactionModelRepository, times(0)).findAllByTypesIdAndDataBetween(0, LocalDate.of(2020, 11, 13),
                LocalDate.of(2020, 11, 19));
        verifyNoMoreInteractions(informationDTOConverter);
    }

    @Test
    public void informationModels_Found() {
        TypeTransactionModel type = new TypeTransactionModel().setId(1).setAssignment("travels");
        Set<TypeTransactionModel> types = new HashSet<>();
        types.add(type);
        List<TypeTransactionModel> typeList = new ArrayList<>(types);
        doReturn(typeList).when(typeModelRepository).findAllByAssignmentIsStartingWith("tr");

        List<TransactionInformationModel> informationModelList = new ArrayList<>();
        AccountModel account = new AccountModel().setId(1L).setNameAccount("travel")
                .setBalance(BigDecimal.valueOf(5000)).setCurrency("$");
        TransactionInformationModel model = new TransactionInformationModel().setId(1).setAccountTo(account)
                .setSum(BigDecimal.valueOf(1.1)).setData(LocalDate.of(2020, 11, 15)).setTypes(types);
        informationModelList.add(model);
        doReturn(informationModelList).when(transactionModelRepository).findAllByTypesIdAndDataBetween(1, LocalDate.of(2020, 11, 13), LocalDate.of(2020, 11, 19));

        List<TransactionInformationDTO> transactionInformationDTOList = new ArrayList<>();
        TransactionInformationDTO transactionInformationDTO = new TransactionInformationDTO().setId(1).setTransfer("income")
                .setSum(BigDecimal.valueOf(1.1)).setData(LocalDate.of(2020, 11, 15));
        transactionInformationDTOList.add(transactionInformationDTO);
        doReturn(transactionInformationDTOList).when(informationDTOConverter).convert(informationModelList);

        List<TransactionInformationDTO> list = subj.informationModels("tr", LocalDate.of(2020, 11, 13),
                LocalDate.of(2020, 11, 19));

        assertNotNull(list);
        assertEquals(transactionInformationDTOList, list);

        verify(typeModelRepository, times(1)).findAllByAssignmentIsStartingWith("tr");
        verify(transactionModelRepository, times(1)).findAllByTypesIdAndDataBetween(1, LocalDate.of(2020, 11, 13),
                LocalDate.of(2020, 11, 19));
        verify(informationDTOConverter, times(1)).convert(informationModelList);
    }
}