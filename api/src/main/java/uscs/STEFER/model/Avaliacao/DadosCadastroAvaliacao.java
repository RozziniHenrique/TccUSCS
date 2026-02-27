package uscs.STEFER.model.Avaliacao;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroAvaliacao(
        @NotNull @Min(1) @Max(5) Integer nota,
        String comentario
) {
}
