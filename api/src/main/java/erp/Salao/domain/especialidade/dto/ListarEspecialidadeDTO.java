package erp.Salao.domain.especialidade.dto;

import erp.Salao.domain.especialidade.Especialidade;
import java.math.BigDecimal;

public record ListarEspecialidadeDTO(
  Long id,
  String nome,
  String descricao,
  BigDecimal preco,
  Boolean ativo
) {
  public ListarEspecialidadeDTO(Especialidade especialidade) {
    this(
      especialidade.getId(),
      especialidade.getNome(),
      especialidade.getDescricao(),
      especialidade.getPreco(),
      especialidade.getAtivo()
    );
  }
}
