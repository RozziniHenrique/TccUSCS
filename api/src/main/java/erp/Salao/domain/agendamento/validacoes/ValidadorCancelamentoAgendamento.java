package erp.Salao.domain.agendamento.validacoes;

import erp.Salao.domain.agendamento.dto.CancelarAgendamentoDTO;

public interface ValidadorCancelamentoAgendamento {
    void validar(CancelarAgendamentoDTO dados);
}
