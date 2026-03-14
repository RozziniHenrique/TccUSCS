package erp.Salao.domain.funcionario.dto;

import erp.Salao.domain.especialidade.Especialidade;
import erp.Salao.domain.funcionario.Funcionario;
import java.util.Set;

public record ListarFuncionarioDTO(
  Long id,
  String nome,
  String email,
  Boolean ativo,
  Set<Especialidade> especialidades
) {
  public ListarFuncionarioDTO(Funcionario funcionario) {
    this(
      funcionario.getId(),
      funcionario.getNome(),
      funcionario.getEmail(),
      funcionario.getAtivo(),
      funcionario.getEspecialidades()
    );
  }
}
