package uscs.STEFER.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uscs.STEFER.domain.agendamento.Agendamento;
import uscs.STEFER.domain.agendamento.AgendamentoRepository;
import uscs.STEFER.domain.agendamento.dto.dtoAgendamentoCadastrar;
import uscs.STEFER.domain.agendamento.dto.dtoAgendamentoCancelar;
import uscs.STEFER.domain.agendamento.dto.dtoAgendamentoDetalhar;
import uscs.STEFER.domain.agendamento.validacoes.ValidadorAgendamento;
import uscs.STEFER.domain.agendamento.validacoes.ValidadorCancelamentoAgendamento;
import uscs.STEFER.domain.cliente.ClienteRepository;
import uscs.STEFER.domain.especialidade.EspecialidadeRepository;
import uscs.STEFER.domain.funcionario.Funcionario;
import uscs.STEFER.domain.funcionario.FuncionarioRepository;
import uscs.STEFER.infra.EmailService;
import uscs.STEFER.infra.exception.ValidacaoException;

import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EspecialidadeRepository especialidadeRepository;
    @Autowired
    private List<ValidadorAgendamento> validadoresAgendamento;
    @Autowired
    private List<ValidadorCancelamentoAgendamento> validadoresCancelamento;
    @Autowired
    private EmailService emailService;

    @Transactional
    public dtoAgendamentoDetalhar agendar(dtoAgendamentoCadastrar dados) {
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

        var agendamento = new Agendamento(funcionario, cliente, especialidade, dados.data());
        agendamentoRepository.save(agendamento);

        emailService.enviarConfirmacao(agendamento);

        return new dtoAgendamentoDetalhar(agendamento);
    }

    @Transactional
    public void cancelar(dtoAgendamentoCancelar dados) {
        if (!agendamentoRepository.existsById(dados.idAgendamento())) {
            throw new EntityNotFoundException("Id do agendamento informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var agendamento = agendamentoRepository.getReferenceById(dados.idAgendamento());
        agendamento.setMotivoCancelamento(dados.motivo());
    }

    private Funcionario escolherFuncionario(dtoAgendamentoCadastrar dados) {
        if (dados.idFuncionario() != null) {
            return funcionarioRepository.getReferenceById(dados.idFuncionario());
        }

        if (dados.idEspecialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando funcionário não for escolhido!");
        }

        return funcionarioRepository.escolherFuncionarioAleatorioLivreNaData(dados.idEspecialidade(), dados.data());
    }
}