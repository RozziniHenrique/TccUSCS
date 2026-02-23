package uscs.STEFER.model.Especialidade;

import jakarta.validation.constraints.NotBlank;

public record EspecialidadeCadastro(

        @NotBlank
        String nome,

        String descricao) {
}