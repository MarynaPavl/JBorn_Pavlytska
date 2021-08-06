package ru.pavlytskaya.api.apiController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pavlytskaya.api.json.*;
import ru.pavlytskaya.service.TypeDTO;
import ru.pavlytskaya.service.TypeService;
import ru.pavlytskaya.api.apiConverter.TypeTransactionModelToResponseConverter;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TypeController {
    private final TypeService typeService;
    private final TypeTransactionModelToResponseConverter converter;

    @GetMapping("get-type-info")
    public @ResponseBody
    ResponseEntity<TypeInformationResponse> typeInformation(@RequestBody @Valid TypeInformationRequest request) {
        List<TypeDTO> typeDTOS = typeService.typeInformation(request.getString());
        if (typeDTOS == null) {
            return status(HttpStatus.NOT_FOUND).build();
        }
        return ok(converter.convert(typeDTOS));
    }

    @PostMapping("type-create")
    public @ResponseBody
    ResponseEntity<TypeCreatResponse> typeCreate(@RequestBody @Valid TypeCreatRequest request) {
        TypeDTO typeDTO = typeService.typeCreat(request.getAssignment());
        if (typeDTO == null) {
            return status(HttpStatus.BAD_REQUEST).build();
        }
        return ok(converter.convert(typeDTO));
    }

    @DeleteMapping("type-delete")
    public @ResponseBody
    ResponseEntity<String> typeDelete(@RequestBody @Valid TypeDeleteRequest request) {
        int delete = typeService.delete(request.getAssigmentId());
        if (delete == 0) {
            return status(HttpStatus.NOT_FOUND).build();
        }
        return ok("successfully");
    }
}
