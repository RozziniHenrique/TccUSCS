package uscs.STEFER.model.Funcionario;

import uscs.STEFER.model.Especialidade.EspecialidadeDetalhamento;
import uscs.STEFER.model.endereco.Endereco;
import java.util.Set;
import java.util.stream.Collectors;

public record FuncionarioDetalhamento(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco,
        Set<EspecialidadeDetalhamento> especialidades
) {

    public FuncionarioDetalhamento(Funcionario funcionario) {
        this(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getEmail(),
                funcionario.getTelefone(),
                funcionario.getCpf(),
                funcionario.getEndereco(),
                funcionario.getEspecialidades().stream()
                        .map(e -> new EspecialidadeDetalhamento(e))
                        .collect(Collectors.toSet())
        );
    }
}
