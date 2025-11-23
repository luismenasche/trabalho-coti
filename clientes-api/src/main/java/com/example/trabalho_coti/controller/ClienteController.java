package com.example.trabalho_coti.controller;

import com.example.trabalho_coti.dto.controller.ClienteRequestDTO;
import com.example.trabalho_coti.dto.controller.ClienteRequestComIdDTO;
import com.example.trabalho_coti.dto.controller.ClienteResponseDTO;
import com.example.trabalho_coti.dto.error.ErroCPFDTO;
import com.example.trabalho_coti.dto.error.ErroIdDTO;
import com.example.trabalho_coti.dto.error.ErroJsonDTO;
import com.example.trabalho_coti.dto.error.ErroValidacaoDTO;
import com.example.trabalho_coti.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Endpoints para leitura, inserção, edição e remoção de clientes no banco de dados")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Obtém todos os clientes cadastrados no banco")
    public ResponseEntity<List<ClienteResponseDTO>> getAll() {
        List<ClienteResponseDTO> response = clienteService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Obtém do banco o cliente com o id informado",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ErroIdDTO.class))
                    )
            })
    public ResponseEntity<ClienteResponseDTO> getById(@PathVariable UUID id) {
        ClienteResponseDTO response = clienteService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Cadastra um novo cliente no banco",
            responses = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ErroJsonDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            content = @Content(schema = @Schema(implementation = ErroValidacaoDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            content = @Content(schema = @Schema(implementation = ErroCPFDTO.class))
                    )
            })
    public ResponseEntity<ClienteResponseDTO> post(@RequestBody @Valid ClienteRequestDTO cliente) {
        ClienteResponseDTO response = clienteService.create(cliente);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(
            summary = "Edita os dados do cliente informado",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ErroJsonDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            content = @Content(schema = @Schema(implementation = ErroValidacaoDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            content = @Content(schema = @Schema(implementation = ErroCPFDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ErroIdDTO.class))
                    )
            })
    public ResponseEntity<ClienteResponseDTO> put(@RequestBody @Valid ClienteRequestComIdDTO cliente) {
        ClienteResponseDTO response = clienteService.update(cliente);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Remove do banco o cliente com o id informado",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ErroIdDTO.class))
                    )
            })
    public ResponseEntity<ClienteResponseDTO> delete(@PathVariable UUID id) {
        ClienteResponseDTO response = clienteService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}