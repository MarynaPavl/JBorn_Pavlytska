package ru.pavlytskaya.service;

import ru.pavlytskaya.converter.Converter;
import ru.pavlytskaya.dao.InformationDao;
import ru.pavlytskaya.dao.TransactionInformationModel;

import java.time.LocalDate;
import java.util.List;

public class InformationService {
    private final InformationDao informationDao;
    private final Converter<TransactionInformationModel, InformationDTO> informationDTOConverter;

    public InformationService(InformationDao informationDao, Converter<TransactionInformationModel, InformationDTO> informationDTOConverter) {
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
