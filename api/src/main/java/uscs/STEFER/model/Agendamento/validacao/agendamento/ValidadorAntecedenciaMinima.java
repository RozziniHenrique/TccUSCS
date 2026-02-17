package uscs.STEFER.model.Agendamento.validacao.agendamento;

import org.springframework.stereotype.Component;
import uscs.STEFER.infra.ValidacaoException;
import uscs.STEFER.model.Agendamento.DadosAgendamento;
import uscs.STEFER.model.Agendamento.validacao.ValidadorAgendamento;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorAntecedenciaMinima implements ValidadorAgendamento {
    public void validar(DadosAgendamento dados) {
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, dados.data()).toMinutes();

        if (diferencaEmMinutos < 30) {
            throw new ValidacaoException("O agendamento deve ser feito com no mínimo 30 minutos de antecedência!");
        }
    }
}
