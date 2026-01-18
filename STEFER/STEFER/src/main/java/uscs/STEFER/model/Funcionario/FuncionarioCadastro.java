package uscs.STEFER.model.Funcionario;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import uscs.STEFER.model.endereco.dadoEndereco;

import java.util.List;

public record FuncionarioCadastro(

        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String telefone,

        @NotBlank
        @Size(min = 11, max = 11)
        String cpf,

        @NotNull
        @Valid
        dadoEndereco endereco,

        @NotNull
        List<Long> especialidadesIds
) {
}
