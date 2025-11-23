package com.example.mail_sender.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class MensagemClienteDTO {
    private UUID id;

    private String nome;

    private String email;

    private String cpf;

    private LocalDate dataNascimento;

    private List<EnderecoDTO> enderecos;
}
