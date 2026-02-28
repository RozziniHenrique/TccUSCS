package uscs.STEFER.domain.especialidade.dto;

import uscs.STEFER.domain.especialidade.Especialidade;

public record dtoEspecialidadeDetalhar(Long id, String nome, String descricao) {
    public dtoEspecialidadeDetalhar(Especialidade especialidade) {
        this(especialidade.getId(), especialidade.getNome(), especialidade.getDescricao());
    }
}
