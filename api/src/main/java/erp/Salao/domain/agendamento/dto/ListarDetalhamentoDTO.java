package erp.Salao.domain.agendamento.dto;

import erp.Salao.domain.agendamento.Agendamento;
import java.time.LocalDateTime;

public record ListarDetalhamentoDTO(
  Long id,
  String nomeCliente,
  String nomeFuncionario,
  String especialidade,
  LocalDateTime data,
  Boolean concluido,
  Integer nota,
  String motivoCancelamento
) {
  public ListarDetalhamentoDTO(Agendamento agendamento) {
    this(
      agendamento.getId(),
      agendamento.getCliente().getNome(),
      agendamento.getFuncionario().getNome(),
      agendamento.getEspecialidade().getNome(),
      agendamento.getData(),
      agendamento.getConcluido(),
      agendamento.getNota(),
      agendamento.getMotivoCancelamento()
    );
  }
}
