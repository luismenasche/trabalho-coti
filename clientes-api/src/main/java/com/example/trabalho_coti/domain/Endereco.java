package com.example.trabalho_coti.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Entity
@Data
@ToString(exclude = {"cliente"})
@EqualsAndHashCode(exclude = {"cliente"})
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String logradouro;

    private String complemento;

    private String numero;

    private String bairro;

    private String cidade;

    private String uf;

    private String cep;

    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;
}
