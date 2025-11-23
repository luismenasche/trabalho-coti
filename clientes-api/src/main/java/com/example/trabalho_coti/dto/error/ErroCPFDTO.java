package com.example.trabalho_coti.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "Erro CPF Duplicado")
public class ErroCPFDTO {
    @Schema(example = "Já existe um cliente cadastrado com este CPF")
    private final String erro = "Já existe um cliente cadastrado com este CPF";
}
