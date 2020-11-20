package ru.pavlytskaya.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.pavlytskaya.comverter.AccountModelToAccountDTOConverter;
import ru.pavlytskaya.dao.AccountDao;
import ru.pavlytskaya.dao.AccountModel;

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
        accountModel.setBalance(999.8);
        accountModel.setCurrency("$");
        accountModel.setUserID(1);
        accountModelList.add(accountModel);
        when(accountDao.listOfAccount(1)).thenReturn(accountModelList);
        List<AccountDTO> accountDTOList = new ArrayList<>();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        accountDTO.setNameAccount("main");
        accountDTO.setBalance(999.8);
        accountDTO.setCurrency("$");
        accountDTO.setUserID(1);
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

        when(accountDao.creatAccount("name", 22.2, "$", 1)).thenReturn(null);

        List<AccountDTO> creat = subj.accountCreat("name", 22.2, "$", 1);

        assertNull(creat);

        verify(accountDao, times(1)).creatAccount("name", 22.2, "$", 1);
        verifyNoMoreInteractions(accountDTOConverter);
    }

    @Test
    public void accountCreat_Successful() {
        List<AccountModel> accountModelList = new ArrayList<>();
        AccountModel accountModel = new AccountModel();
        accountModel.setId(1);
        accountModel.setNameAccount("name");
        accountModel.setBalance(22.2);
        accountModel.setCurrency("$");
        accountModel.setUserID(1);
        accountModelList.add(accountModel);
        when(accountDao.creatAccount("name", 22.2, "$", 1)).thenReturn(accountModelList);
        List<AccountDTO> accountDTOList = new ArrayList<>();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        accountDTO.setNameAccount("name");
        accountDTO.setBalance(22.2);
        accountDTO.setCurrency("$");
        accountDTO.setUserID(1);
        accountDTOList.add(accountDTO);
        when(accountDTOConverter.convert(accountModelList)).thenReturn(accountDTOList);

        List<AccountDTO> creat = subj.accountCreat("name", 22.2, "$", 1);

        assertNotNull(creat);
        assertEquals(accountDTOList, creat);

        verify(accountDao, times(1)).creatAccount("name", 22.2, "$", 1);
        verify(accountDTOConverter, times(1)).convert(accountModelList);

    }

    @Test
    public void deleteAccount() {
        subj.deleteAccount(1);
        verify(accountDao, times(1)).delete(1);
    }
}