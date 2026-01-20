package uscs.STEFER.model.Funcionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Page<Funcionario> findAllByAtivoTrue(Pageable paginacao);
    @Query(value = """
    SELECT * FROM funcionarios f
    WHERE f.ativo = 1
    AND f.id NOT IN (
        SELECT a.funcionario_id FROM agendamentos a
        WHERE a.data = :data
    )
    ORDER BY RAND()
    LIMIT 1
    """, nativeQuery = true)
    Funcionario escolherFuncionarioAleatorioLivreNaData(LocalDateTime data);
}