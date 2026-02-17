package uscs.STEFER.model.Especialidade;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import uscs.STEFER.model.endereco.dadoEndereco;

public record EspecialidadeCadastro(

        @NotBlank
        String nome,

        String descricao) {
}