package uscs.STEFER.model.Agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uscs.STEFER.model.Cliente.ClienteRepository;
import uscs.STEFER.model.Especialidade.EspecialidadeRepository;
import uscs.STEFER.model.Funcionario.FuncionarioRepository;
import uscs.STEFER.model.Funcionario.Funcionario;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.DayOfWeek;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired private ClienteRepository clienteRepository;
    @Autowired private EspecialidadeRepository especialidadeRepository;

    public void agendar(DadosAgendamento dados) {
        var cliente = clienteRepository.findById(dados.idCliente())
                .filter(c -> c.getAtivo())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado ou inativo!"));

        var especialidade = especialidadeRepository.findById(dados.idEspecialidade())
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada!"));

        if (agendamentoRepository.existsByClienteIdAndData(dados.idCliente(), dados.data())) {
            throw new RuntimeException("Cliente já possui um agendamento nesse horário!");
        }

        var funcionario = escolherFuncionario(dados);

        validarHorarioFuncionamento(dados.data());
        validarAntecedenciaMinima(dados.data());

        if (agendamentoRepository.existsByFuncionarioIdAndData(funcionario.getId(), dados.data())) {
            throw new RuntimeException("Este funcionário já possui um agendamento neste horário!");
        }

        var agendamento = new Agendamento(null, funcionario, cliente, especialidade, dados.data());
        agendamentoRepository.save(agendamento);
    }

    private Funcionario escolherFuncionario(DadosAgendamento dados) {
        if (dados.idFuncionario() != null) {
            return funcionarioRepository.findById(dados.idFuncionario())
                    .filter(f -> f.getAtivo())
                    .orElseThrow(() -> new RuntimeException("Funcionário não encontrado ou inativo!"));
        }

        var funcionarioAleatorio = funcionarioRepository.escolherFuncionarioAleatorioLivreNaData(dados.data());
        if (funcionarioAleatorio == null) {
            throw new RuntimeException("Não há funcionários disponíveis para este horário!");
        }
        return funcionarioAleatorio;
    }

    private void validarHorarioFuncionamento(LocalDateTime data) {
        var domingo = data.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAbertura = data.getHour() < 7;
        var depoisDoEncerramento = data.getHour() > 18;

        if (domingo || antesDaAbertura || depoisDoEncerramento) {
            throw new RuntimeException("Fora do horário de funcionamento (Segunda a Sábado, 07:00 às 19:00)");
        }
    }

    private void validarAntecedenciaMinima(LocalDateTime data) {
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, data).toMinutes();

        if (diferencaEmMinutos < 30) {
            throw new RuntimeException("O agendamento deve ser feito com no mínimo 30 minutos de antecedência!");
        }
    }
}