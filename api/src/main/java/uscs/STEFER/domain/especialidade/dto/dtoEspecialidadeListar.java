package uscs.STEFER.domain.especialidade.dto;

import uscs.STEFER.domain.especialidade.Especialidade;

public record dtoEspecialidadeListar(Long id, String nome, String descricao) {

    public dtoEspecialidadeListar(Especialidade especialidade) {
        this(especialidade.getId(), especialidade.getNome(), especialidade.getDescricao());
    }
}