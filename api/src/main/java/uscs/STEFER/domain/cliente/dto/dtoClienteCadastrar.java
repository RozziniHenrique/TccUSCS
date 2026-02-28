package uscs.STEFER.domain.cliente.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import uscs.STEFER.domain.endereco.dtoEndereco;
import uscs.STEFER.domain.usuario.UsuarioRole;

public record dtoClienteCadastrar(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String senha,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{11,14}")
        String cpf,
        @NotNull
        @Valid
        dtoEndereco endereco,
        @NotNull
        UsuarioRole role) {
}

