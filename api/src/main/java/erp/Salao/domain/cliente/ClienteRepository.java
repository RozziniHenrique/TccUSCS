package erp.Salao.domain.cliente;

import erp.Salao.domain.cliente.dto.ListarClienteDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
  Page<Cliente> findAllByAtivoTrue(Pageable paginacao);

  List<Cliente> findByNome(String nome);
}
