package com.example.trabalho_coti.service;

import com.example.trabalho_coti.domain.Cliente;
import com.example.trabalho_coti.dto.basic.EnderecoComIdDTO;
import com.example.trabalho_coti.dto.controller.ClienteRequestComIdDTO;
import com.example.trabalho_coti.dto.controller.ClienteRequestDTO;
import com.example.trabalho_coti.dto.controller.ClienteResponseDTO;
import com.example.trabalho_coti.exception.CPFDuplicadoException;
import com.example.trabalho_coti.exception.IdNotFoundException;
import com.example.trabalho_coti.repository.ClienteRepository;
import org.junit.jupiter.api.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.UUID;

import static com.example.trabalho_coti.common.GeradorCliente.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteServiceTest {
    private ClienteRepository repository;
    private ClienteService service;

    @BeforeEach
    public void setUp() {
        repository = mock(ClienteRepository.class);

        RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
        Queue queue = mock(Queue.class);

        service = new ClienteService(repository, rabbitTemplate, queue);
    }

    @Test
    @Order(1)
    @DisplayName("GET de todos os clientes")
    public void getAllTestOk() {
        Cliente clienteEntidade1 = gerarClienteEntidadeSalva();
        Cliente clienteEntidade2 = gerarClienteEntidadeSalva();
        List<Cliente> listaClientesEntidade = List.of(clienteEntidade1, clienteEntidade2);
        ClienteResponseDTO clienteResponse1 = gerarClienteResponse(clienteEntidade1);
        ClienteResponseDTO clienteResponse2 = gerarClienteResponse(clienteEntidade2);

        when(repository.getAllOrdenado()).thenReturn(listaClientesEntidade);

        List<ClienteResponseDTO> listaRetornada =  service.getAll();

        assertArrayEquals(listaRetornada.toArray(), List.of(clienteResponse1, clienteResponse2).toArray());
    }

    /////

    @Test
    @Order(2)
    @DisplayName("GET de cliente por Id - Id existe")
    public void getByIdTestOk() {
        Cliente clienteEntidade = gerarClienteEntidadeSalva();
        UUID id = clienteEntidade.getId();
        ClienteResponseDTO clienteResponse = gerarClienteResponse(clienteEntidade);

        when(repository.getPorId(id)).thenReturn(clienteEntidade);

        ClienteResponseDTO clienteRetornado = service.getById(id);

        assertEquals(clienteRetornado, clienteResponse);
    }

    @Test
    @Order(3)
    @DisplayName("GET de cliente por Id - Id não existe")
    public void getByIdTestThrowNotFound() {
        UUID id = UUID.randomUUID();

        when(repository.getPorId(id)).thenReturn(null);

        assertThrows(IdNotFoundException.class, () -> service.getById(id));
    }

    ////

    @Test
    @Order(4)
    @DisplayName("POST de cliente - Inserção correta")
    public void createTestOk() {
        ClienteRequestDTO clienteRequest = gerarClienteRequest();
        Cliente clienteEntidade = gerarClienteEntidade(clienteRequest);
        Cliente clienteEntidadeSalva = gerarClienteEntidadeSalva(clienteEntidade);
        ClienteResponseDTO clienteResponse = gerarClienteResponse(clienteEntidadeSalva);

        when(repository.existsByCpf(clienteEntidade.getCpf())).thenReturn(false);
        when(repository.save(clienteEntidade)).thenReturn(clienteEntidadeSalva);

        ClienteResponseDTO clienteRetornado = service.create(clienteRequest);

        assertEquals(clienteRetornado, clienteResponse);
    }

    @Test
    @Order(5)
    @DisplayName("POST de cliente - CPF duplicado")
    public void createTestThrowCPFDuplicado() {
        ClienteRequestDTO clienteRequest = gerarClienteRequest();
        Cliente clienteEntidade = gerarClienteEntidade(clienteRequest);

        when(repository.existsByCpf(clienteEntidade.getCpf())).thenReturn(true);

        assertThrows(CPFDuplicadoException.class, () -> service.create(clienteRequest));
    }

    ////

    @Test
    @Order(6)
    @DisplayName("PUT de cliente - Atualização correta")
    public void updateTestOk() {
        ClienteRequestComIdDTO clienteRequest = gerarClienteRequestComId();
        Cliente clienteEntidadeSalva = gerarClienteEntidadeSalva(clienteRequest);
        ClienteResponseDTO clienteResponse = gerarClienteResponse(clienteEntidadeSalva);

        when(repository.getPorId(clienteEntidadeSalva.getId())).thenReturn(clienteEntidadeSalva);
        when(repository.existsByCpf(clienteEntidadeSalva.getCpf())).thenReturn(false);
        when(repository.save(clienteEntidadeSalva)).thenReturn(clienteEntidadeSalva);

        ClienteResponseDTO clienteRetornado = service.update(clienteRequest);

        assertEquals(clienteRetornado, clienteResponse);
    }

    @Test
    @Order(7)
    @DisplayName("PUT de cliente - Id do cliente não existe")
    public void updateTestThrowClienteNotFound() {
        ClienteRequestComIdDTO clienteRequest = gerarClienteRequestComId();

        when(repository.getPorId(clienteRequest.getId())).thenReturn(null);

        assertThrows(IdNotFoundException.class, () -> service.update(clienteRequest));
    }

    @Test
    @Order(8)
    @DisplayName("PUT de cliente - Id do endereço não existe")
    public void updateTestThrowEnderecoNotFound() {
        ClienteRequestComIdDTO clienteRequest = gerarClienteRequestComId();
        Cliente clienteEntidadeSalva = gerarClienteEntidadeSalva(clienteRequest);

        when(repository.getPorId(clienteEntidadeSalva.getId())).thenReturn(clienteEntidadeSalva);

        assertThrows(IdNotFoundException.class, () -> {
                EnderecoComIdDTO endereco = clienteRequest.getEndereco();
                endereco.setId(UUID.randomUUID());
                clienteRequest.setEndereco(endereco);
                service.update(clienteRequest); // tenta atualizar um endereço não cadastrado neste cliente
        });
    }

    @Test
    @Order(9)
    @DisplayName("PUT de cliente - CPF duplicado")
    public void updateTestThrowCPFDuplicado() {
        ClienteRequestComIdDTO clienteRequest = gerarClienteRequestComId();
        Cliente clienteEntidadeSalva = gerarClienteEntidadeSalva(clienteRequest);

        long novoCPFNum = Long.parseLong(clienteRequest.getCpf()) + 1;
        String novoCPF = Long.toString(novoCPFNum);

        when(repository.getPorId(clienteEntidadeSalva.getId())).thenReturn(clienteEntidadeSalva);
        when(repository.existsByCpf(novoCPF)).thenReturn(true);

        assertThrows(CPFDuplicadoException.class, () -> {
            clienteRequest.setCpf(novoCPF);
            service.update(clienteRequest); // tenta atualizar o CPF para outro já existente
        });
    }

    ////

    @Test
    @Order(10)
    @DisplayName("DELETE de cliente - Id existe")
    public void deleteTestOk() {
        Cliente clienteEntidadeSalva = gerarClienteEntidadeSalva();
        UUID id = clienteEntidadeSalva.getId();
        ClienteResponseDTO clienteResponse = gerarClienteResponse(clienteEntidadeSalva);

        when(repository.getPorId(id)).thenReturn(clienteEntidadeSalva);

        ClienteResponseDTO clienteRetornado = service.delete(id);

        assertEquals(clienteRetornado, clienteResponse);
    }

    @Test
    @Order(11)
    @DisplayName("DELETE de cliente - Id não existe")
    public void deleteTestThrowNotFound() {
        UUID id = UUID.randomUUID();

        when(repository.getPorId(id)).thenReturn(null);

        assertThrows(IdNotFoundException.class, () -> service.delete(id));
    }
}
