package ru.pavlytskaya.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.json.TypeInformationRequest;
import ru.pavlytskaya.json.TypeInformationResponse;
import ru.pavlytskaya.service.TypeDTO;
import ru.pavlytskaya.service.TypeService;

import java.util.List;

@Service("/typeInformation")
@RequiredArgsConstructor
public class TypeInformationController implements SecureController<TypeInformationRequest, TypeInformationResponse> {
    private final TypeService typeService;

    @Override
    public TypeInformationResponse handler(TypeInformationRequest request, Long userId) {
        List<TypeDTO> types = typeService.typeInformation();
        if (types != null) {
            return new TypeInformationResponse(types);
        }
        return null;
    }

    @Override
    public Class<TypeInformationRequest> getRequestClass() {
        return TypeInformationRequest.class;
    }
}
