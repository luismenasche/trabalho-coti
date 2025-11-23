package com.example.trabalho_coti.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "Erro Id Não Encontrado")
public class ErroIdDTO {
    @Schema(example = "O Id 3fa85f64-5717-4562-b3fc-2c963f66afa6 não foi encontrado")
    private String erro;

    public static ErroIdDTO build(String id) {
        String erroTemplate = "O Id %s não foi encontrado";
        String erroId = String.format(erroTemplate, id);
        ErroIdDTO erroDTO = new ErroIdDTO();
        erroDTO.setErro(erroId);
        return erroDTO;
    }
}
