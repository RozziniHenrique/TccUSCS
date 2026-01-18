package uscs.STEFER.model.Especialidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
    Page<Especialidade> findAllByAtivoTrue(Pageable paginacao);
}
