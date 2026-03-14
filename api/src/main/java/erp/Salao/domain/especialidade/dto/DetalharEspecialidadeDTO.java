package erp.Salao.domain.especialidade.dto;

import erp.Salao.domain.especialidade.Especialidade;
import java.math.BigDecimal;

public record DetalharEspecialidadeDTO(
  Long id,
  String nome,
  String descricao,
  BigDecimal preco
) {
  public DetalharEspecialidadeDTO(Especialidade especialidade) {
    this(
      especialidade.getId(),
      especialidade.getNome(),
      especialidade.getDescricao(),
      especialidade.getPreco()
    );
  }
}
