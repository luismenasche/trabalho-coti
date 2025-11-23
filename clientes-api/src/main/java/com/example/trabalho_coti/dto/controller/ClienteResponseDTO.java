package com.example.trabalho_coti.dto.controller;

import com.example.trabalho_coti.dto.basic.ClienteDTO;
import com.example.trabalho_coti.dto.basic.EnderecoComIdDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "Cliente Salvo")
public class ClienteResponseDTO extends ClienteDTO {
    private UUID id;
    private List<EnderecoComIdDTO> enderecos;
}
