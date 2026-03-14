package erp.Salao.domain.agendamento.dto;

import erp.Salao.domain.agendamento.Agendamento;
import java.time.LocalDateTime;

public record DetalharAgendamentoDTO(
  Long id,
  Long idFuncionario,
  String nomeFuncionario,
  Long idCliente,
  String nomeCliente,
  Long idEspecialidade,
  String nomeEspecialista,
  LocalDateTime data
) {
  public DetalharAgendamentoDTO(Agendamento agendamento) {
    this(
      agendamento.getId(),
      agendamento.getFuncionario().getId(),
      agendamento.getFuncionario().getNome(),
      agendamento.getCliente().getId(),
      agendamento.getCliente().getNome(),
      agendamento.getEspecialidade().getId(),
      agendamento.getEspecialidade().getNome(),
      agendamento.getData()
    );
  }
}
