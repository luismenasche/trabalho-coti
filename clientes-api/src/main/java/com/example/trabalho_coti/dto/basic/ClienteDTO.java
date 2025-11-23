package com.example.trabalho_coti.dto.basic;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
public class ClienteDTO {
    @NotBlank(message = "O nome não pode estar vazio")
    @Length(min = 8, max = 100, message = "O comprimento do nome deve estar entre 8 e 100 caracteres")
    @Schema(example = "Carlos Silva")
    private String nome;

    @NotBlank(message = "O email não pode estar vazio")
    @Email(message = "O valor informado não é um email válido")
    @Schema(example = "carlos@gmail.com")
    private String email;

    @NotBlank(message = "O CPF não pode estar vazio")
    @Pattern(regexp = "[0-9]{11}", message = "O CPF precisa ser uma sequencia de 11 dígitos (apenas números)")
    @Schema(example = "12345678901")
    private String cpf;

    @NotNull(message = "A data de nascimento precisa ser uma data válida")
    @PastOrPresent(message = "A data de nascimento não pode ser uma data futura")
    @Schema(example = "1991-02-26")
    private LocalDate dataNascimento;
}
