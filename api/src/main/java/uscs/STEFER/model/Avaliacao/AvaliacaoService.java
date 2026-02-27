package uscs.STEFER.model.Avaliacao;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uscs.STEFER.infra.ValidacaoException;
import uscs.STEFER.model.Agendamento.AgendamentoRepository;

@Service
public class AvaliacaoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Transactional
    public void registrarAvaliacao(Long id, DadosCadastroAvaliacao dados) {

        var agendamento = agendamentoRepository.getReferenceById(id);

        if (!agendamento.getConcluido()) {
            throw new ValidacaoException("O agendamento precisa estar CONCLUÍDO!");
        }

        var avaliacao = new Avaliacao(dados, agendamento);
        avaliacaoRepository.save(avaliacao);
    }
}
