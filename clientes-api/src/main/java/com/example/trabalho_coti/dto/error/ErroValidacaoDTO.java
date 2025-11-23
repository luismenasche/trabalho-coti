package com.example.trabalho_coti.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
@Schema(title = "Erro de Validação")
public class ErroValidacaoDTO {
    @Schema(example = "Erro(s) de validação")
    private final String erro = "Erro(s) de validação";

    @Schema(example = "{\"nome\": \"O comprimento do nome deve estar entre 8 e 100 caracteres\"}")
    private final Map<String, String> campos;
}
