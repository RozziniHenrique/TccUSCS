package uscs.STEFER.model.Especialidade;

import jakarta.validation.constraints.NotNull;

public record EspecialidadeAtualizacao(

        @NotNull
        Long id,

        String nome,

        String descricao

) {}