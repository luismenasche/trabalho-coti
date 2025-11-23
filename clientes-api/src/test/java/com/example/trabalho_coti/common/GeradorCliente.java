package com.example.trabalho_coti.common;

import com.example.trabalho_coti.domain.Cliente;
import com.example.trabalho_coti.domain.Endereco;
import com.example.trabalho_coti.dto.basic.EnderecoDTO;
import com.example.trabalho_coti.dto.controller.ClienteRequestComIdDTO;
import com.example.trabalho_coti.dto.controller.ClienteRequestDTO;
import com.example.trabalho_coti.dto.controller.ClienteResponseDTO;
import com.github.javafaker.Faker;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class GeradorCliente {
    private static final Faker faker = new Faker();
    private static final ModelMapper modelMapper = new ModelMapper();

    public static ClienteRequestDTO gerarClienteRequest() {
        ClienteRequestDTO cliente = new ClienteRequestDTO();
        cliente.setNome(faker.name().fullName());
        cliente.setEmail(faker.internet().emailAddress());
        cliente.setDataNascimento(LocalDate.now());
        cliente.setCpf(faker.number().digits(11));
        EnderecoDTO endereco = new EnderecoDTO();
        cliente.setEndereco(endereco);
        return cliente;
    }

    public static ClienteRequestComIdDTO gerarClienteRequestComId() {
        ClienteRequestDTO tempCliente = gerarClienteRequest();
        ClienteRequestComIdDTO cliente = modelMapper.map(tempCliente, ClienteRequestComIdDTO.class);
        cliente.setId(UUID.randomUUID());
        cliente.getEndereco().setId(UUID.randomUUID());
        return cliente;
    }

    public static Cliente gerarClienteEntidade(ClienteRequestDTO clienteRequest) {
        Cliente cliente = modelMapper.map(clienteRequest, Cliente.class);
        Endereco endereco = modelMapper.map(clienteRequest.getEndereco(), Endereco.class);
        endereco.setCliente(cliente);
        cliente.setEnderecos(List.of(endereco));
        return cliente;
    }

    public static Cliente gerarClienteEntidadeSalva(ClienteRequestComIdDTO clienteRequest) {
        Cliente cliente = modelMapper.map(clienteRequest, Cliente.class);
        Endereco endereco = modelMapper.map(clienteRequest.getEndereco(), Endereco.class);
        endereco.setCliente(cliente);
        cliente.setEnderecos(List.of(endereco));
        return cliente;
    }

    public static Cliente gerarClienteEntidadeSalva(Cliente cliente) {
        Cliente clienteSalvo = new Cliente();
        Endereco enderecoSalvo = new Endereco();
        modelMapper.map(cliente, clienteSalvo);
        modelMapper.map(cliente.getEnderecos().getFirst(), enderecoSalvo);
        clienteSalvo.setId(UUID.randomUUID());
        enderecoSalvo.setId(UUID.randomUUID());
        clienteSalvo.setEnderecos(List.of(enderecoSalvo));
        return clienteSalvo;
    }

    public static Cliente gerarClienteEntidadeSalva() {
        ClienteRequestComIdDTO tempCliente = gerarClienteRequestComId();
        return gerarClienteEntidadeSalva(tempCliente);
    }

    public static ClienteResponseDTO gerarClienteResponse(Cliente clienteEntidade) {
        if (clienteEntidade.getId() == null)
            clienteEntidade.setId(UUID.randomUUID());
        Endereco endereco = clienteEntidade.getEnderecos().getFirst();
        if (endereco.getId() == null)
            endereco.setId(UUID.randomUUID());
        return modelMapper.map(clienteEntidade, ClienteResponseDTO.class);
    }

    public static ClienteResponseDTO gerarClienteResponse() {
        Cliente clienteEntidade = gerarClienteEntidadeSalva();
        return gerarClienteResponse(clienteEntidade);
    }
}
