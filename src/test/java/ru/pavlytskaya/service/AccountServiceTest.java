package ru.pavlytskaya.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.pavlytskaya.converter.AccountModelToAccountDTOConverter;
import ru.pavlytskaya.dao.AccountDao;
import ru.pavlytskaya.dao.AccountModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
   @InjectMocks AccountService subj;
   @Mock AccountDao accountDao;
   @Mock AccountModelToAccountDTOConverter accountDTOConverter;


    @Test
    public void accountInformation_NotFound() {
        when(accountDao.listOfAccount(1)).thenReturn(null);

        List<AccountDTO> list = subj.accountInformation(1);

        assertNull(list);

        verify(accountDao, times(1)).listOfAccount(1);
        verifyNoMoreInteractions(accountDTOConverter);
    }

    @Test
    public void accountInformation_Found() {
        List<AccountModel> accountModelList = new ArrayList<>();
        AccountModel accountModel = new AccountModel();
        accountModel.setId(1);
        accountModel.setNameAccount("main");
        accountModel.setBalance(BigDecimal.valueOf(999.8));
        accountModel.setCurrency("$");
        accountModel.getUserModel().setId(1);
        accountModelList.add(accountModel);
        when(accountDao.listOfAccount(1)).thenReturn(accountModelList);
        List<AccountDTO> accountDTOList = new ArrayList<>();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        accountDTO.setNameAccount("main");
        accountDTO.setBalance(BigDecimal.valueOf(999.8));
        accountDTO.setCurrency("$");
        accountDTOList.add(accountDTO);
        when(accountDTOConverter.convert(accountModelList)).thenReturn(accountDTOList);


        List<AccountDTO> list = subj.accountInformation(1);

        assertNotNull(list);
        assertEquals(accountDTOList, list);

        verify(accountDao, times(1)).listOfAccount(1);
        verify(accountDTOConverter, times(1)).convert(accountModelList);
    }

    @Test
    public void accountNotCreat() {

        when(accountDao.creatAccount("name", BigDecimal.valueOf(22.2), "$", 1)).thenReturn(null);

        AccountDTO creat = subj.accountCreat("name", BigDecimal.valueOf(22.2), "$", 1);

        assertNull(creat);

        verify(accountDao, times(1)).creatAccount("name", BigDecimal.valueOf(22.2), "$", 1);
        verifyNoMoreInteractions(accountDTOConverter);
    }

    @Test
    public void accountCreat_Successful() {
        AccountModel accountModel = new AccountModel();
        accountModel.setId(1);
        accountModel.setNameAccount("name");
        accountModel.setBalance(BigDecimal.valueOf(22.2));
        accountModel.setCurrency("$");
        when(accountDao.creatAccount("name", BigDecimal.valueOf(22.2), "$", 1)).thenReturn(accountModel);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        accountDTO.setNameAccount("name");
        accountDTO.setBalance(BigDecimal.valueOf(22.2));
        accountDTO.setCurrency("$");
        when(accountDTOConverter.convert(accountModel)).thenReturn(accountDTO);

        AccountDTO creat = subj.accountCreat("name", BigDecimal.valueOf(22.2), "$", 1);

        assertNotNull(creat);
        assertEquals(accountDTO, creat);

        verify(accountDao, times(1)).creatAccount("name", BigDecimal.valueOf(22.2), "$", 1);
        verify(accountDTOConverter, times(1)).convert(accountModel);

    }

    @Test
    public void deleteAccount() {
        subj.deleteAccount(1);
        verify(accountDao, times(1)).delete(1);
    }
}