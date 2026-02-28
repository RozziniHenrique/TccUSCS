package uscs.STEFER.model.Especialidade;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record EspecialidadeAtualizacao(

        @NotNull
        Long id,

        String nome,

        @Positive
        BigDecimal preco,

        String descricao

) {
}