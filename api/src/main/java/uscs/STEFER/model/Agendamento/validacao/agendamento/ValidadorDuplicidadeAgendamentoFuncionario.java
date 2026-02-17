package uscs.STEFER.model.Agendamento.validacao.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uscs.STEFER.infra.ValidacaoException;
import uscs.STEFER.model.Agendamento.AgendamentoRepository;
import uscs.STEFER.model.Agendamento.DadosAgendamento;
import uscs.STEFER.model.Agendamento.validacao.ValidadorAgendamento;

@Component
public class ValidadorDuplicidadeAgendamentoFuncionario implements ValidadorAgendamento {
    @Autowired
    private AgendamentoRepository repository;

    public void validar(DadosAgendamento dados) {
        if (dados.idFuncionario() == null) return;
        var possuiAgendamento = repository.existsByFuncionarioIdAndDataAndMotivoCancelamentoIsNull(dados.idFuncionario(), dados.data());
        if (possuiAgendamento) {
            throw new ValidacaoException("Funcionário já possui outro agendamento nesse mesmo horário!");
        }
    }
}
