package erp.Salao.domain.agendamento.validacoes;

import erp.Salao.domain.agendamento.dto.CadastrarAgendamentoDTO;

public interface ValidadorAgendamento {
  void validar(CadastrarAgendamentoDTO dados);
}
