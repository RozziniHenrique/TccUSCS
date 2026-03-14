package erp.Salao.domain.agendamento.validacoes;

import erp.Salao.domain.agendamento.AgendamentoRepository;
import erp.Salao.domain.agendamento.dto.CadastrarAgendamentoDTO;
import erp.Salao.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDuplicidadeAgendamentoCliente
  implements ValidadorAgendamento
{

  @Autowired
  private AgendamentoRepository repository;

  public void validar(CadastrarAgendamentoDTO dados) {
    var possuiAgendamento =
      repository.existsByClienteIdAndDataAndMotivoCancelamentoIsNull(
        dados.idCliente(),
        dados.data()
      );
    if (possuiAgendamento) {
      throw new ValidacaoException(
        "Cliente já possui um agendamento nesse mesmo horário!"
      );
    }
  }
}
