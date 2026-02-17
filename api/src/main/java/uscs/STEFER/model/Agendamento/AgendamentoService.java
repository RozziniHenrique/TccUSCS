package uscs.STEFER.model.Agendamento;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uscs.STEFER.infra.ValidacaoException;
import uscs.STEFER.model.Agendamento.validacao.ValidadorAgendamento;
import uscs.STEFER.model.Agendamento.validacao.cancelamento.ValidadorCancelamentoAgendamento;
import uscs.STEFER.model.Cliente.ClienteRepository;
import uscs.STEFER.model.Especialidade.EspecialidadeRepository;
import uscs.STEFER.model.Funcionario.FuncionarioRepository;
import uscs.STEFER.model.Funcionario.Funcionario;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    @Autowired private AgendamentoRepository agendamentoRepository;
    @Autowired private FuncionarioRepository funcionarioRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private EspecialidadeRepository especialidadeRepository;
    @Autowired private List<ValidadorAgendamento> validadoresAgendamento;
    @Autowired private List<ValidadorCancelamentoAgendamento> validadoresCancelamento;

    @Transactional
    public AgendamentoDetalhamento agendar(DadosAgendamento dados) {
        if (!clienteRepository.existsById(dados.idCliente())) {
            throw new EntityNotFoundException("Id do cliente informado não existe!");
        }

        if (dados.idFuncionario() != null && !funcionarioRepository.existsById(dados.idFuncionario())) {
            throw new EntityNotFoundException("Id do funcionário informado não existe!");
        }

        validadoresAgendamento.forEach(v -> v.validar(dados));

        var cliente = clienteRepository.getReferenceById(dados.idCliente());
        var especialidade = especialidadeRepository.getReferenceById(dados.idEspecialidade());
        var funcionario = escolherFuncionario(dados);

        var agendamento = new Agendamento(null, funcionario, cliente, especialidade, dados.data(), null);
        agendamentoRepository.save(agendamento);

        return new AgendamentoDetalhamento(agendamento);
    }

    @Transactional
    public void cancelar(DadosCancelamentoAgendamento dados) {
        if (!agendamentoRepository.existsById(dados.idAgendamento())) {
            throw new EntityNotFoundException("Id do agendamento informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var agendamento = agendamentoRepository.getReferenceById(dados.idAgendamento());
        agendamento.setMotivoCancelamento(dados.motivo());
    }

    private Funcionario escolherFuncionario(DadosAgendamento dados) {
        if (dados.idFuncionario() != null) {
            return funcionarioRepository.getReferenceById(dados.idFuncionario());
        }

        if (dados.idEspecialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando funcionário não for escolhido!");
        }

        return funcionarioRepository.escolherFuncionarioAleatorioLivreNaData(dados.idEspecialidade(), dados.data());
    }
}