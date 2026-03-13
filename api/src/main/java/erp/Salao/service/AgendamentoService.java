package erp.Salao.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import erp.Salao.domain.agendamento.Agendamento;
import erp.Salao.domain.agendamento.AgendamentoRepository;
import erp.Salao.domain.agendamento.dto.*;
import erp.Salao.domain.agendamento.validacoes.ValidadorAgendamento;
import erp.Salao.domain.agendamento.validacoes.ValidadorCancelamentoAgendamento;
import erp.Salao.domain.avaliacao.Avaliacao;
import erp.Salao.domain.avaliacao.AvaliacaoRepository;
import erp.Salao.domain.avaliacao.dto.CadastrarAvaliacaoDTO;
import erp.Salao.domain.avaliacao.dto.DetalharAvaliacaoDTO;
import erp.Salao.domain.cliente.ClienteRepository;
import erp.Salao.domain.especialidade.EspecialidadeRepository;
import erp.Salao.domain.funcionario.Funcionario;
import erp.Salao.domain.funcionario.FuncionarioRepository;
import erp.Salao.domain.usuario.Usuario;
import erp.Salao.domain.usuario.UsuarioRole;
import erp.Salao.infra.exception.ValidacaoException;

import java.time.LocalDate;
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
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private List<ValidadorAgendamento> validadoresAgendamento;
    @Autowired
    private List<ValidadorCancelamentoAgendamento> validadoresCancelamento;

    @Transactional
    public DetalharAgendamentoDTO agendar(CadastrarAgendamentoDTO dados) {
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

        if(dados.idCliente() == 1){
            agendamento.setConcluido(true);
        }
            agendamentoRepository.save(agendamento);
        return new DetalharAgendamentoDTO(agendamento);
    }

    public Page<ListarDetalhamentoDTO> listar(LocalDate data, Long idFuncionario, Long idCliente, Long idEspecialidade, Pageable paginacao, Usuario logado) {
        if (logado.getRole() == UsuarioRole.GESTOR || logado.getRole() == UsuarioRole.ADMIN) {
            return agendamentoRepository.findAllComFiltros(data, idFuncionario, idCliente, idEspecialidade, paginacao)
                    .map(ListarDetalhamentoDTO::new);
        }
        return agendamentoRepository.buscaPersonalizada(logado.getId(), paginacao)
                .map(ListarDetalhamentoDTO::new);
    }

    public DetalharAgendamentoDTO detalhar(Long id) {
        var agendamento = agendamentoRepository.findByIdAndMotivoCancelamentoIsNull(id)
                .orElseThrow(() -> new ValidacaoException("Agendamento não encontrado ou já cancelado!"));
        return new DetalharAgendamentoDTO(agendamento);
    }

    @Transactional
    public void finalizar(FinalizarAgendamentoDTO dados) {
        var agendamento = agendamentoRepository.getReferenceById(dados.id());
        agendamento.finalizar(dados.nota());
    }

    @Transactional
    public DetalharAvaliacaoDTO avaliar(Long id, CadastrarAvaliacaoDTO dados) {
        var agendamento = agendamentoRepository.getReferenceById(id);
        if (avaliacaoRepository.existsByAgendamentoId(id)) {
            throw new ValidacaoException("Este agendamento já foi avaliado!");
        }
        var avaliacao = new Avaliacao(dados, agendamento);
        avaliacaoRepository.save(avaliacao);
        return new DetalharAvaliacaoDTO(avaliacao);
    }

    @Transactional
    public void cancelar(CancelarAgendamentoDTO dados) {
        if (!agendamentoRepository.existsById(dados.idAgendamento())) {
            throw new EntityNotFoundException("Id do agendamento informado não existe!");
        }
        validadoresCancelamento.forEach(v -> v.validar(dados));
        var agendamento = agendamentoRepository.getReferenceById(dados.idAgendamento());
        agendamento.setMotivoCancelamento(dados.motivo());
    }

    private Funcionario escolherFuncionario(CadastrarAgendamentoDTO dados) {
        if (dados.idFuncionario() != null) {
            return funcionarioRepository.getReferenceById(dados.idFuncionario());
        }
        if (dados.idEspecialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando funcionário não for escolhido!");
        }
        return funcionarioRepository.escolherFuncionarioAleatorioLivreNaData(dados.idEspecialidade(), dados.data());
    }
}