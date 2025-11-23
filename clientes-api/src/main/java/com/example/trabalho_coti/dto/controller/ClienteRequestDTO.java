package com.example.trabalho_coti.dto.controller;

import com.example.trabalho_coti.dto.basic.ClienteDTO;
import com.example.trabalho_coti.dto.basic.EnderecoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "Dados de Cliente")
public class ClienteRequestDTO extends ClienteDTO {
    private EnderecoDTO endereco;
}
