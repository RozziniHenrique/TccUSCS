package uscs.STEFER.model.Agendamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoAgendamento(
        @NotNull
        Long idAgendamento,

        @NotBlank
        String motivo
) {}
