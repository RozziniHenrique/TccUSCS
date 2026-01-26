package uscs.STEFER.model.Agendamento;

import java.time.LocalDateTime;

public record AgendamentoDetalhamento(
        Long id,
        Long idFuncionario,
        String nomeFuncionario,
        Long idCliente,
        Long idEspecialidade,
        String nomeEspecialista,
        LocalDateTime data) {
    public AgendamentoDetalhamento(Agendamento agendamento) {
        this(
                agendamento.getId(),
                agendamento.getFuncionario().getId(),
                agendamento.getFuncionario().getNome(),
                agendamento.getCliente().getId(),
                agendamento.getEspecialidade().getId(),
                agendamento.getEspecialidade().getNome(),
                agendamento.getData());
    }
}
