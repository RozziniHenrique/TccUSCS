package uscs.STEFER.model.Agendamento;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    @Query("SELECT a FROM Agendamento a WHERE a.motivoCancelamento IS NULL")
    Page<Agendamento> findAllByMotivoCancelamentoIsNull(Pageable paginacao);

    boolean existsByFuncionarioIdAndDataAndMotivoCancelamentoIsNull(Long idFuncionario, LocalDateTime data);

    boolean existsByClienteIdAndDataAndMotivoCancelamentoIsNull(Long idCliente, LocalDateTime data);

    Optional<Agendamento> findByIdAndMotivoCancelamentoIsNull(Long id);
}
