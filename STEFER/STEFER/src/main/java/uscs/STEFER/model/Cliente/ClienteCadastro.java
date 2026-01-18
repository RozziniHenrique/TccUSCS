package uscs.STEFER.model.Cliente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import uscs.STEFER.model.endereco.dadoEndereco;

public record ClienteCadastro(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{11,14}")
        String cpf,
        @NotNull
        @Valid
        dadoEndereco endereco) {
}

