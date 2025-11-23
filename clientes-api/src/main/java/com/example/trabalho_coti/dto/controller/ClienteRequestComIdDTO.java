package com.example.trabalho_coti.dto.controller;

import com.example.trabalho_coti.dto.basic.ClienteDTO;
import com.example.trabalho_coti.dto.basic.EnderecoComIdDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "Dados de Cliente com Id")
public class ClienteRequestComIdDTO extends ClienteDTO {
    private UUID id;
    private EnderecoComIdDTO endereco;
}
