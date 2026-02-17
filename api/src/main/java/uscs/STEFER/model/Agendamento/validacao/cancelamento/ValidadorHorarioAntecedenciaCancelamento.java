package uscs.STEFER.model.Agendamento.validacao.cancelamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uscs.STEFER.infra.ValidacaoException;
import uscs.STEFER.model.Agendamento.AgendamentoRepository;
import uscs.STEFER.model.Agendamento.DadosCancelamentoAgendamento;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedenciaCancelamento implements ValidadorCancelamentoAgendamento {

    @Autowired
    private AgendamentoRepository repository;

    @Override
    public void validar(DadosCancelamentoAgendamento dados) {
        var agendamento = repository.findById(dados.idAgendamento())
                .orElseThrow(() -> new ValidacaoException("Id do agendamento informado não existe!"));

        var agora = LocalDateTime.now();
        var dataAgendamento = agendamento.getData();
        var diferencaEmHoras = Duration.between(agora, dataAgendamento).toHours();

        if (diferencaEmHoras < 2) {
            throw new ValidacaoException("Agendamento somente pode ser cancelado com antecedência mínima de 2h!");
        }
    }
}
