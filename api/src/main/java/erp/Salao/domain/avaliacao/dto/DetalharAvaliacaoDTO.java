package erp.Salao.domain.avaliacao.dto;

import erp.Salao.domain.avaliacao.Avaliacao;

public record DetalharAvaliacaoDTO(
        Long id,
        Integer nota,
        String comentario,
        Long idAgendamento,
        String nomeFuncionario
) {
    public DetalharAvaliacaoDTO(Avaliacao avaliacao) {
        this(
                avaliacao.getId(),
                avaliacao.getNota(),
                avaliacao.getComentario(),
                avaliacao.getAgendamento().getId(),
                avaliacao.getAgendamento().getFuncionario().getNome()
        );
    }
}
