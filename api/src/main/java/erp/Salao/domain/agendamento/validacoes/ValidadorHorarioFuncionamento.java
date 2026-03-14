package erp.Salao.domain.agendamento.validacoes;

import erp.Salao.domain.agendamento.dto.CadastrarAgendamentoDTO;
import erp.Salao.infra.exception.ValidacaoException;
import java.time.DayOfWeek;
import org.springframework.stereotype.Component;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamento {

  public void validar(CadastrarAgendamentoDTO dados) {
    var data = dados.data();
    var domingo = data.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    var antesDaAbertura = data.getHour() < 9;
    var depoisDoEncerramento = data.getHour() > 21;

    if (domingo || antesDaAbertura || depoisDoEncerramento) {
      throw new ValidacaoException(
        "Fora do horário de funcionamento (Segunda a Sábado, 09:00 às 22:00)"
      );
    }
  }
}
