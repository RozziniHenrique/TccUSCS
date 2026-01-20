package uscs.STEFER.model.Agendamento;

import java.time.LocalDateTime;

public record AgendamentoDetalhamento(
        Long id,
        Long idFuncionario,
        Long idCliente,
        LocalDateTime data) {
    public AgendamentoDetalhamento(Agendamento agendamento) {
        this(
                agendamento.getId(),
                agendamento.getFuncionario().getId(),
                agendamento.getCliente().getId(),
                agendamento.getData());
    }
}
