package erp.Salao.domain.funcionario.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

import erp.Salao.domain.endereco.CadastrarEnderecoDTO;

public record AtualizarFuncionarioDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        CadastrarEnderecoDTO endereco,
        List<Long> especialidadesIds
) {
}
