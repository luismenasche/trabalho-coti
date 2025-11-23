package com.example.trabalho_coti.dto.basic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@Schema(title = "Endereço com Id")
@EqualsAndHashCode(callSuper = true)
public class EnderecoComIdDTO extends EnderecoDTO {
    private UUID id;
}
