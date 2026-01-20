package uscs.STEFER.model.Agendamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uscs.STEFER.model.Cliente.Cliente;

import java.time.LocalDateTime;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByFuncionarioIdAndData(Long idFuncionario, LocalDateTime data);

    boolean existsByClienteIdAndData(Long idCliente, LocalDateTime data);
}
