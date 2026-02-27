package uscs.STEFER.model.Avaliacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    Boolean existsByAgendamentoId(Long idAgendamento);

    Optional<Avaliacao> findByAgendamentoId(Long idAgendamento);

    @Query("SELECT AVG(av.nota) FROM Avaliacao av WHERE av.agendamento.funcionario.id = :idFunc")
    Double getMediaNotasPorFuncionario(@Param("idFunc") Long idFunc);
}
