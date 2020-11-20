package ru.pavlytskaya.service;

import ru.pavlytskaya.comverter.TransactionInformationModelToInformationDTOConverter;
import ru.pavlytskaya.dao.InformationDao;
import ru.pavlytskaya.dao.TransactionInformationModel;

import java.time.LocalDate;
import java.util.List;

public class InformationService {
    private final InformationDao informationDao;
    private final TransactionInformationModelToInformationDTOConverter informationDTOConverter;

    public InformationService(InformationDao informationDao, TransactionInformationModelToInformationDTOConverter informationDTOConverter) {
        this.informationDao = informationDao;
        this.informationDTOConverter = informationDTOConverter;
    }

    public List<InformationDTO> informationModels(long assignmentId, LocalDate fromDate, LocalDate toData) {
        List<TransactionInformationModel> informationModels = informationDao.informationModelList(assignmentId, fromDate, toData);
        if (informationModels == null) {
            return null;
        }
        return informationDTOConverter.convert(informationModels);
    }
}
