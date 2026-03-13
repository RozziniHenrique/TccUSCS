package erp.Salao.domain.especialidade.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AtualizarEspecialidadeDTO(

        @NotNull
        Long id,

        String nome,

        @Positive
        BigDecimal preco,

        String descricao

) {
}