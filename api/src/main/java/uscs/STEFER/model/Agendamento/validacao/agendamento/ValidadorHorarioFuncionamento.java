package uscs.STEFER.model.Agendamento.validacao.agendamento;

import org.springframework.stereotype.Component;
import uscs.STEFER.infra.ValidacaoException;
import uscs.STEFER.model.Agendamento.DadosAgendamento;
import uscs.STEFER.model.Agendamento.validacao.ValidadorAgendamento;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamento {

    public void validar(DadosAgendamento dados) {
        var data = dados.data();
        var domingo = data.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAbertura = data.getHour() < 9;
        var depoisDoEncerramento = data.getHour() > 21;

        if (domingo || antesDaAbertura || depoisDoEncerramento) {
            throw new ValidacaoException("Fora do horário de funcionamento (Segunda a Sábado, 09:00 às 22:00)");
        }
    }
}
