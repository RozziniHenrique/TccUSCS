package uscs.STEFER.model.Agendamento.validacao.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uscs.STEFER.infra.ValidacaoException;
import uscs.STEFER.model.Agendamento.AgendamentoRepository;
import uscs.STEFER.model.Agendamento.DadosAgendamento;
import uscs.STEFER.model.Agendamento.validacao.ValidadorAgendamento;

@Component
public class ValidadorDuplicidadeAgendamentoCliente implements ValidadorAgendamento {
    @Autowired private AgendamentoRepository repository;

    public void validar(DadosAgendamento dados) {
        var possuiAgendamento = repository.existsByClienteIdAndDataAndMotivoCancelamentoIsNull(dados.idCliente(), dados.data());
        if (possuiAgendamento) {
            throw new ValidacaoException("Cliente já possui um agendamento nesse mesmo horário!");
        }
    }
}
