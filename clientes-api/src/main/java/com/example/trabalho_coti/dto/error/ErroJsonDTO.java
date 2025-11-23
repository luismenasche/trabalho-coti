package com.example.trabalho_coti.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "Erro JSON Mal Formado")
public class ErroJsonDTO {
    @Schema(example = "O corpo da requisição não está em um formato JSON válido")
    private final String erro = "O corpo da requisição não está em um formato JSON válido";
}
