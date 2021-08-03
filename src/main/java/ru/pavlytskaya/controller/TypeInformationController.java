package ru.pavlytskaya.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.api.json.TypeInformationRequest;
import ru.pavlytskaya.api.json.TypeInformationResponse;
import ru.pavlytskaya.service.TypeDTO;
import ru.pavlytskaya.service.TypeService;

import java.util.List;

@Service("/typeInformation")
@RequiredArgsConstructor
public class TypeInformationController implements SecureController<TypeInformationRequest, TypeInformationResponse> {
    private final TypeService typeService;

    @Override
    public TypeInformationResponse handler(TypeInformationRequest request, Long userId) {
        List<TypeDTO> types = typeService.typeInformation(request.getString());
        return new TypeInformationResponse(types);
    }

    @Override
    public Class<TypeInformationRequest> getRequestClass() {
        return TypeInformationRequest.class;
    }
}
