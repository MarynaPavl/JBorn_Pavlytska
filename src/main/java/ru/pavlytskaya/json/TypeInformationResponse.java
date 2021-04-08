package ru.pavlytskaya.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.pavlytskaya.service.TypeDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class TypeInformationResponse {
    private List<TypeDTO> types;
}
