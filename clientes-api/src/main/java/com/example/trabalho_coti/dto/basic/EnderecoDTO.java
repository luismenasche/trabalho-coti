package com.example.trabalho_coti.dto.basic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "Endereço")
public class EnderecoDTO {
    @Schema(example = "Rua São Francisco Xavier")
    private String logradouro;

    @Schema(example = "apt 501")
    private String complemento;

    @Schema(example = "524")
    private String numero;

    @Schema(example = "Maracanã")
    private String bairro;

    @Schema(example = "Rio de Janeiro")
    private String cidade;

    @Schema(example = "RJ")
    private String uf;

    @Schema(example = "22250020")
    private String cep;
}
