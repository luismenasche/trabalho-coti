package com.example.trabalho_coti.controller;

import com.example.trabalho_coti.dto.controller.ClienteRequestComIdDTO;
import com.example.trabalho_coti.dto.controller.ClienteRequestDTO;
import com.example.trabalho_coti.dto.controller.ClienteResponseDTO;
import com.example.trabalho_coti.dto.error.ErroCPFDTO;
import com.example.trabalho_coti.dto.error.ErroIdDTO;
import com.example.trabalho_coti.dto.error.ErroJsonDTO;
import com.example.trabalho_coti.dto.error.ErroValidacaoDTO;
import com.example.trabalho_coti.exception.CPFDuplicadoException;
import com.example.trabalho_coti.exception.IdNotFoundException;
import com.example.trabalho_coti.service.ClienteService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.trabalho_coti.common.GeradorCliente.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ClienteController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteControllerTest {
    @MockitoBean
    private ClienteService clienteService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String endpoint = "/api/clientes";

    @Test
    @Order(1)
    @DisplayName("GET de todos os clientes - Retorno 200 (OK)")
    public void getAllTest200() throws Exception {
        ClienteResponseDTO cliente1 = gerarClienteResponse();
        ClienteResponseDTO cliente2 = gerarClienteResponse();
        List<ClienteResponseDTO> listaClientes = List.of(cliente1, cliente2);

        when(clienteService.getAll()).thenReturn(listaClientes);

        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(listaClientes)));
    }

    ////

    @Test
    @Order(2)
    @DisplayName("GET de cliente por Id - Retorno 200 (OK)")
    public void getByIdTest200() throws Exception {
        ClienteResponseDTO cliente = gerarClienteResponse();
        UUID id = cliente.getId();

        when(clienteService.getById(id)).thenReturn(cliente);

        mockMvc.perform(get(endpoint + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(cliente)));
    }

    @Test
    @Order(3)
    @DisplayName("GET de cliente por Id - Retorno 404 (Not Found)")
    public void getByIdTest404() throws Exception {
        UUID id = UUID.randomUUID();
        ErroIdDTO erro = ErroIdDTO.build(id.toString());

        when(clienteService.getById(id)).thenThrow(new IdNotFoundException(id));

        mockMvc.perform(get(endpoint + "/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(erro)));
    }

    ////

    @Test
    @Order(4)
    @DisplayName("POST de cliente - Retorno 201 (Created)")
    public void createTest201() throws Exception {
        ClienteRequestDTO clienteRequest = gerarClienteRequest();
        ClienteResponseDTO clienteResponse = gerarClienteResponse();

        when(clienteService.create(clienteRequest)).thenReturn(clienteResponse);

        String content = objectMapper.writeValueAsString(clienteRequest);

        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(clienteResponse)));
    }

    @Test
    @Order(5)
    @DisplayName("POST de cliente - Retorno 400 (Bad Request)")
    public void createTest400() throws Exception {
        ClienteRequestDTO clienteRequest = gerarClienteRequest();
        ErroJsonDTO erro = new ErroJsonDTO();

        String content = objectMapper.writeValueAsString(clienteRequest);
        content = content.replace(",", ";"); // cria Json mal formado

        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(erro)));
    }

    @Test
    @Order(6)
    @DisplayName("POST de cliente - Retorno 409 (Conflict / CPF Duplicado)")
    public void createTest409() throws Exception {
        ClienteRequestDTO clienteRequest = gerarClienteRequest();
        ErroCPFDTO erro = new ErroCPFDTO();

        String content = objectMapper.writeValueAsString(clienteRequest);

        when(clienteService.create(clienteRequest)).thenThrow(CPFDuplicadoException.class);

        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isConflict())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(erro)));
    }

    @Test
    @Order(7)
    @DisplayName("POST de cliente - Retorno 422 (Unprocessable Entity / Falha na Validação)")
    public void createTest422() throws Exception {
        ClienteRequestDTO clienteRequest = gerarClienteRequest();
        clienteRequest.setNome("Rui"); // nome inválido de acordo com as regras de validação
        Map<String, String> campos = new HashMap<>();
        campos.put("nome", "O comprimento do nome deve estar entre 8 e 100 caracteres");
        ErroValidacaoDTO erro = new ErroValidacaoDTO(campos);

        String content = objectMapper.writeValueAsString(clienteRequest);

        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(erro)));
    }

    ////

    @Test
    @Order(8)
    @DisplayName("PUT de cliente - Retorno 200 (OK)")
    public void updateTest200() throws Exception {
        ClienteRequestComIdDTO clienteRequest = gerarClienteRequestComId();
        ClienteResponseDTO clienteResponse = gerarClienteResponse();

        when(clienteService.update(clienteRequest)).thenReturn(clienteResponse);

        String content = objectMapper.writeValueAsString(clienteRequest);

        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(clienteResponse)));
    }

    @Test
    @Order(9)
    @DisplayName("PUT de cliente - Retorno 400 (Bad Request)")
    public void updateTest400() throws Exception {
        ClienteRequestComIdDTO clienteRequest = gerarClienteRequestComId();
        ErroJsonDTO erro = new ErroJsonDTO();

        String content = objectMapper.writeValueAsString(clienteRequest);
        content = content.replace(",", ";"); // cria Json mal formado

        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(erro)));
    }

    @Test
    @Order(10)
    @DisplayName("PUT de cliente - Retorno 404 (Not Found)")
    public void updateTest404() throws Exception {
        ClienteRequestComIdDTO clienteRequest = gerarClienteRequestComId();
        UUID id = clienteRequest.getId();
        ErroIdDTO erro = ErroIdDTO.build(id.toString());

        when(clienteService.update(clienteRequest)).thenThrow(new IdNotFoundException(id));

        String content = objectMapper.writeValueAsString(clienteRequest);

        mockMvc.perform(put(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(erro)));
    }

    @Test
    @Order(11)
    @DisplayName("PUT de cliente - Retorno 409 (Conflict / CPF Duplicado)")
    public void updateTest409() throws Exception {
        ClienteRequestComIdDTO clienteRequest = gerarClienteRequestComId();
        ErroCPFDTO erro = new ErroCPFDTO();

        String content = objectMapper.writeValueAsString(clienteRequest);

        when(clienteService.update(clienteRequest)).thenThrow(CPFDuplicadoException.class);

        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isConflict())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(erro)));
    }

    @Test
    @Order(12)
    @DisplayName("PUT de cliente - Retorno 422 (Unprocessable Entity / Falha na Validação)")
    public void updateTest422() throws Exception {
        ClienteRequestComIdDTO clienteRequest = gerarClienteRequestComId();
        clienteRequest.setNome("Rui"); // nome inválido de acordo com as regras de validação
        Map<String, String> campos = new HashMap<>();
        campos.put("nome", "O comprimento do nome deve estar entre 8 e 100 caracteres");
        ErroValidacaoDTO erro = new ErroValidacaoDTO(campos);

        String content = objectMapper.writeValueAsString(clienteRequest);

        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(erro)));
    }

    ////

    @Test
    @Order(13)
    @DisplayName("DELETE de cliente - Retorno 200 (OK)")
    public void deleteTest200() throws Exception {
        ClienteResponseDTO clienteResponse = gerarClienteResponse();
        UUID id = clienteResponse.getId();

        when(clienteService.delete(id)).thenReturn(clienteResponse);

        mockMvc.perform(delete(endpoint + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(clienteResponse)));
    }

    @Test
    @Order(14)
    @DisplayName("DELETE de cliente - Retorno 404 (Not Found)")
    public void deleteTest404() throws Exception {
        UUID id = UUID.randomUUID();
        ErroIdDTO erro = ErroIdDTO.build(id.toString());

        when(clienteService.delete(id)).thenThrow(new IdNotFoundException(id));

        mockMvc.perform(delete(endpoint + "/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(erro)));
    }
}
