package uscs.STEFER.model.Especialidade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record EspecialidadeCadastro(

        @NotBlank
        String nome,

        @NotNull
        @Positive
        BigDecimal preco,

        String descricao) {
}