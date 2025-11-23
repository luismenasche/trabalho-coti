package com.example.mail_sender.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EnderecoDTO {
    private UUID id;

    private String logradouro;

    private String complemento;

    private String numero;

    private String bairro;

    private String cidade;

    private String uf;

    private String cep;
}
