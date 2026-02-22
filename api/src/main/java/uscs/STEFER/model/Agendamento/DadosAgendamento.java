package uscs.STEFER.model.Agendamento;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamento(
        Long idFuncionario,

        @NotNull
        Long idCliente,

        @NotNull
        Long idEspecialidade,

        @NotNull
        @Future
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime data
) {
}

