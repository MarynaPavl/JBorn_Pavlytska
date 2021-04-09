package ru.pavlytskaya.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.json.AboutTransByTypeForTimeRequest;
import ru.pavlytskaya.json.AboutTransByTypeForTimeResponse;
import ru.pavlytskaya.service.TransactionInformationDTO;
import ru.pavlytskaya.service.TransactionInformationService;

import java.time.LocalDate;
import java.util.List;

@Service("/aboutTransByTypeForTime")
@RequiredArgsConstructor
public class AboutTransByTypeForTimeController implements SecureController<AboutTransByTypeForTimeRequest, AboutTransByTypeForTimeResponse> {
    private final TransactionInformationService informationService;

    @Override
    public AboutTransByTypeForTimeResponse handler(AboutTransByTypeForTimeRequest request, Long userId) {
        List<TransactionInformationDTO> information = informationService.informationModels(
                request.getAssignmentId(), LocalDate.parse(request.getFromDate()), LocalDate.parse(request.getToData()));
        return new AboutTransByTypeForTimeResponse(information);
    }

    @Override
    public Class<AboutTransByTypeForTimeRequest> getRequestClass() {
        return AboutTransByTypeForTimeRequest.class;
    }
}
