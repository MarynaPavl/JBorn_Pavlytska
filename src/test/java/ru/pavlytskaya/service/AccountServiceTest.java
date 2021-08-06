package ru.pavlytskaya.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.pavlytskaya.converter.AccountModelToAccountDTOConverter;
import ru.pavlytskaya.entity.AccountModel;
import ru.pavlytskaya.entity.UserModel;
import ru.pavlytskaya.repository.AccountModelRepository;
import ru.pavlytskaya.repository.UserModelRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.*;


@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @InjectMocks
    AccountService subj;
    @Mock
    AccountModelRepository accountModelRepository;
    @Mock
    UserModelRepository userModelRepository;
    @Mock
    AccountModelToAccountDTOConverter accountDTOConverter;


    @Test
    public void accountInformation_NotFound() {
        when(accountModelRepository.findAllByUserId(1L)).thenReturn(null);

        List<AccountDTO> list = subj.accountInformation(1L);

        assertNull(list);

        verify(accountModelRepository, times(1)).findAllByUserId(1L);
        verifyNoMoreInteractions(accountDTOConverter);
    }

    @Test
    public void accountInformation_Found() {
        List<AccountModel> accountModelList = new ArrayList<>();
        AccountModel accountModel = new AccountModel().setId(1).setNameAccount("main")
                .setBalance(BigDecimal.valueOf(999.8)).setCurrency("$");
        accountModelList.add(accountModel);
        doReturn(accountModelList).when(accountModelRepository).findAllByUserId(1L);

        List<AccountDTO> accountDTOList = new ArrayList<>();
        AccountDTO accountDTO = new AccountDTO().setId(1).setNameAccount("main")
                .setBalance(BigDecimal.valueOf(999.8)).setCurrency("$");
        accountDTOList.add(accountDTO);
        doReturn(accountDTOList).when(accountDTOConverter).convert(accountModelList);

        List<AccountDTO> list = subj.accountInformation(1);

        assertNotNull(list);
        assertEquals(accountDTOList, list);

        verify(accountModelRepository, times(1)).findAllByUserId(1L);
        verify(accountDTOConverter, times(1)).convert(accountModelList);
    }

    @Test
    public void accountNotCreat() {
        AccountModel accountModel = new AccountModel();
        UserModel user = userModelRepository.findUserById(1L);
        doReturn(null).when(accountModelRepository).save(accountModel.setNameAccount("name").setBalance(BigDecimal.valueOf(22.2))
                .setCurrency("$").setUser(user));

        AccountDTO accountDTO = subj.accountCreat("name", BigDecimal.valueOf(22.2), "$", 1);

        assertNull(accountDTO);

        verify(accountModelRepository, times(1)).save(accountModel);
        verify(userModelRepository, times(2)).findUserById(1L);
        verify(accountDTOConverter, times(1)).convert(accountModel);
    }

    @Test
    public void accountCreat_Successful() {
        AccountModel accountModel = new AccountModel();
        UserModel user = userModelRepository.findUserById(1L);
        doReturn(accountModel).when(accountModelRepository).save(accountModel.setNameAccount("name")
                .setBalance(BigDecimal.valueOf(22.2)).setCurrency("$").setUser(user));

        AccountDTO accountDTO = new AccountDTO().setId(1).setNameAccount("name")
                .setBalance(BigDecimal.valueOf(22.2)).setCurrency("$");

        doReturn(accountDTO).when(accountDTOConverter).convert(accountModel);

        AccountDTO account = subj.accountCreat("name", BigDecimal.valueOf(22.2), "$", 1);

        assertNotNull(account);
        assertEquals(accountDTO, account);

        verify(accountModelRepository, times(1)).save(accountModel);
        verify(userModelRepository, times(2)).findUserById(1L);
        verify(accountDTOConverter, times(1)).convert(accountModel);

    }

    @Test
    public void deleteAccount() {
        doThrow(EmptyResultDataAccessException.class).when(accountModelRepository).deleteById(1L);
        subj.deleteAccount(1);
        verify(accountModelRepository, times(1)).deleteById(1L);
    }
}