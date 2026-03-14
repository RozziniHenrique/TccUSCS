package erp.Salao.domain.funcionario.dto;

import erp.Salao.domain.endereco.Endereco;
import erp.Salao.domain.especialidade.dto.DetalharEspecialidadeDTO;
import erp.Salao.domain.funcionario.Funcionario;
import java.util.Set;
import java.util.stream.Collectors;

public record DetalharFuncionarioDTO(
  Long id,
  String nome,
  String email,
  String telefone,
  String cpf,
  Endereco endereco,
  Set<DetalharEspecialidadeDTO> especialidades
) {
  public DetalharFuncionarioDTO(Funcionario funcionario) {
    this(
      funcionario.getId(),
      funcionario.getNome(),
      funcionario.getEmail(),
      funcionario.getTelefone(),
      funcionario.getCpf(),
      funcionario.getEndereco(),
      funcionario
        .getEspecialidades()
        .stream()
        .map(e -> new DetalharEspecialidadeDTO(e))
        .collect(Collectors.toSet())
    );
  }
}
