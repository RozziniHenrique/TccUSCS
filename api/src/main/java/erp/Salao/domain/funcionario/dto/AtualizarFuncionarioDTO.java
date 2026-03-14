package erp.Salao.domain.funcionario.dto;

import erp.Salao.domain.endereco.CadastrarEnderecoDTO;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AtualizarFuncionarioDTO(
  @NotNull Long id,
  String nome,
  String telefone,
  CadastrarEnderecoDTO endereco,
  List<Long> especialidadesIds
) {}
