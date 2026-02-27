package uscs.STEFER.model.Agendamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uscs.STEFER.model.Agendamento.relatorio.DadosRelatorioEspecialidade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    @Query("SELECT a FROM Agendamento a WHERE a.motivoCancelamento IS NULL")
    Page<Agendamento> findAllByMotivoCancelamentoIsNull(Pageable paginacao);

    boolean existsByFuncionarioIdAndDataAndMotivoCancelamentoIsNull(Long idFuncionario, LocalDateTime data);

    boolean existsByClienteIdAndDataAndMotivoCancelamentoIsNull(Long idCliente, LocalDateTime data);

    Optional<Agendamento> findByIdAndMotivoCancelamentoIsNull(Long id);

    @Query("""
            SELECT a FROM Agendamento a
            WHERE (:data IS NULL OR CAST(a.data AS date) = :data)
            AND (:idFunc IS NULL OR a.funcionario.id = :idFunc)
            AND (:idCli IS NULL OR a.cliente.id = :idCli)
            AND (:idEspec IS NULL OR a.especialidade.id = :idEspec)
            AND a.motivoCancelamento IS NULL
            """)
    Page<Agendamento> findAllComFiltros(
            @Param("data") LocalDate data,
            @Param("idFunc") Long idFunc,
            @Param("idCli") Long idCli,
            @Param("idEspec") Long idEspec,
            Pageable paginacao
    );

    @Query("""
            SELECT new uscs.STEFER.model.Agendamento.relatorio.DadosRelatorioEspecialidade(e.nome, COUNT(a))
            FROM Agendamento a
            JOIN a.especialidade e
            WHERE a.motivoCancelamento IS NULL
            GROUP BY e.nome
            ORDER BY COUNT(a) DESC
            """)
    List<DadosRelatorioEspecialidade> contagemPorEspecialidade();

    @Query("""
            SELECT a FROM Agendamento a 
            WHERE a.concluido = true 
            AND NOT EXISTS (SELECT av FROM Avaliacao av WHERE av.agendamento = a)
            """)
    List<Agendamento> buscarAgendamentosConcluidosSemAvaliacao();
}
