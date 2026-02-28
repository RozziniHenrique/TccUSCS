package uscs.STEFER.model.Agendamento.relatorio;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DadosFinalizarAgendamento(
        @NotNull Long id,
        @NotNull @Min(1) @Max(5) Integer nota
) {
}
