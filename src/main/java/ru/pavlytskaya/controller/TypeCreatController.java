package ru.pavlytskaya.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.api.json.TypeCreatRequest;
import ru.pavlytskaya.api.json.TypeCreatResponse;
import ru.pavlytskaya.service.TypeDTO;
import ru.pavlytskaya.service.TypeService;

@Service("/typeCreat")
@RequiredArgsConstructor
public class TypeCreatController implements SecureController<TypeCreatRequest, TypeCreatResponse> {
    private final TypeService typeService;

    @Override
    public TypeCreatResponse handler(TypeCreatRequest request, Long userId) {
        TypeDTO typeDTO = typeService.typeCreat(request.getAssignment());
        if (typeDTO != null) {
            return new TypeCreatResponse(typeDTO.getId(), typeDTO.getAssignment());
        }
        return null;
    }

    @Override
    public Class<TypeCreatRequest> getRequestClass() {
        return TypeCreatRequest.class;
    }
}
