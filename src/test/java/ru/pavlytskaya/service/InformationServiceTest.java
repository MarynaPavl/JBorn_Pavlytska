package ru.pavlytskaya.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.pavlytskaya.converter.TransactionInformationModelToInformationDTOConverter;
import ru.pavlytskaya.dao.InformationDao;
import ru.pavlytskaya.dao.TransactionInformationModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InformationServiceTest {
    @InjectMocks InformationService subj;
    @Mock InformationDao informationDao;
    @Mock TransactionInformationModelToInformationDTOConverter informationDTOConverter;

    @Test
    public void informationModels_NotFound() {
        when(informationDao.informationModelList(1, LocalDate.of(2020, 11, 13),
                LocalDate.of(2020, 11, 19))).thenReturn(null);
        List<InformationDTO> list = subj.informationModels(1, LocalDate.of(2020, 11, 13),
                LocalDate.of(2020, 11, 19));

        assertNull(list);

        verify(informationDao, times(1)).informationModelList(1, LocalDate.of(2020, 11, 13),
                LocalDate.of(2020, 11, 19));
        verifyNoMoreInteractions(informationDTOConverter);
    }

    @Test
    public void informationModels_Found(){
        List<TransactionInformationModel> informationModelList = new ArrayList<>();
        TransactionInformationModel model = new TransactionInformationModel();
        model.setId(1);
        model.setAccountFrom(1);
        model.setAccountTo(null);
        model.setSum(1.1);
        model.setData(LocalDate.of(2020,11,15));
        informationModelList.add(model);
        when(informationDao.informationModelList(1, LocalDate.of(2020, 11, 13),
                LocalDate.of(2020, 11, 19))).thenReturn(informationModelList);
        List<InformationDTO> informationDTOList = new ArrayList<>();
        InformationDTO informationDTO = new InformationDTO();
        informationDTO.setId(1);
        informationDTO.setTransfer("expense");
        informationDTO.setSum(1.1);
        informationDTO.setData(LocalDate.of(2020, 11, 15));
        informationDTOList.add(informationDTO);
        when(informationDTOConverter.convert(informationModelList)).thenReturn(informationDTOList);

        List<InformationDTO> list = subj.informationModels(1, LocalDate.of(2020, 11, 13),
                LocalDate.of(2020, 11, 19));

        assertNotNull(list);
        assertEquals(informationDTOList, list);

        verify(informationDao, times(1)).informationModelList(1, LocalDate.of(2020, 11, 13),
                LocalDate.of(2020, 11, 19));
        verify(informationDTOConverter, times(1)).convert(informationModelList);
    }
}