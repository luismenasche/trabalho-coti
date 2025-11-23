package com.example.trabalho_coti.service;

import com.example.trabalho_coti.domain.Cliente;
import com.example.trabalho_coti.domain.Endereco;
import com.example.trabalho_coti.dto.controller.ClienteRequestDTO;
import com.example.trabalho_coti.dto.controller.ClienteRequestComIdDTO;
import com.example.trabalho_coti.dto.controller.ClienteResponseDTO;
import com.example.trabalho_coti.exception.CPFDuplicadoException;
import com.example.trabalho_coti.exception.IdNotFoundException;
import com.example.trabalho_coti.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final Queue mailQueue;

    private final ModelMapper modelMapper = new ModelMapper();

    private ClienteResponseDTO salvar(ClienteRequestComIdDTO cliente) {
        Cliente clienteEntidade = modelMapper.map(cliente, Cliente.class);
        Endereco enderecoEntidade = modelMapper.map(cliente.getEndereco(), Endereco.class);
        clienteEntidade.setEnderecos(montarListaEnderecos(enderecoEntidade));
        enderecoEntidade.setCliente(clienteEntidade);
        Cliente clienteEntidadeSalva = repository.save(clienteEntidade);
        return modelMapper.map(clienteEntidadeSalva, ClienteResponseDTO.class);
    }

    private List<Endereco> montarListaEnderecos(Endereco endereco) {
        List<Endereco> listaEnderecos = new ArrayList<>();
        listaEnderecos.add(endereco);
        return listaEnderecos;
    }

    public List<ClienteResponseDTO> getAll() {
        return repository.getAllOrdenado()
                .stream()
                .map(cliente -> modelMapper.map(cliente, ClienteResponseDTO.class))
                .toList();
    }

    public ClienteResponseDTO getById(UUID id) {
        Cliente cliente = repository.getPorId(id);
        if (cliente == null)
            throw new IdNotFoundException(id);
        return modelMapper.map(cliente, ClienteResponseDTO.class);
    }

    public ClienteResponseDTO create(ClienteRequestDTO cliente) {
        if (repository.existsByCpf(cliente.getCpf()))
            throw new CPFDuplicadoException();
        ClienteResponseDTO clienteSalvo = salvar(modelMapper.map(cliente, ClienteRequestComIdDTO.class));
        rabbitTemplate.convertAndSend("", mailQueue.getName(), clienteSalvo);
        return clienteSalvo;
    }

    public ClienteResponseDTO update(ClienteRequestComIdDTO cliente) {
        UUID clienteId = cliente.getId();
        Cliente clienteEntidade = repository.getPorId(clienteId);
        if (clienteEntidade == null)
            throw new IdNotFoundException(clienteId);
        List<Endereco> enderecosEntidade = clienteEntidade.getEnderecos();
        UUID enderecoId = cliente.getEndereco().getId();
        Endereco enderecoBuscado = enderecosEntidade
                .stream()
                .filter(endereco -> endereco.getId().equals(enderecoId))
                .findFirst()
                .orElse(null);
        if (enderecoBuscado == null)
            throw new IdNotFoundException(enderecoId);
        String clienteCPF = cliente.getCpf();
        if (!Objects.equals(clienteCPF, clienteEntidade.getCpf()) && // alteração de CPF
                repository.existsByCpf(clienteCPF))
            throw new CPFDuplicadoException();
        return salvar(cliente);
    }

    public ClienteResponseDTO delete(UUID id) {
        Cliente cliente = repository.getPorId(id);
        if (cliente == null)
            throw new IdNotFoundException(id);
        repository.delete(cliente);
        return modelMapper.map(cliente, ClienteResponseDTO.class);
    }
}
