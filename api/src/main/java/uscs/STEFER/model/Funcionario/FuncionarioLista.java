package uscs.STEFER.model.Funcionario;

import uscs.STEFER.model.Especialidade.Especialidade;

import java.util.Set;

public record FuncionarioLista(Long id, String nome, String email, Set<Especialidade> especialidades) {

    public FuncionarioLista(Funcionario funcionario) {
        this(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getEmail(),
                funcionario.getEspecialidades());
    }
}