package uscs.STEFER.model.Agendamento;

import java.time.LocalDate;

public record DadosFiltroAgendamento(
        LocalDate data,
        Long idFuncionario,
        Long idCliente
) {
}
