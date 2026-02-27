package uscs.STEFER.model.Avaliacao;

public record DadosDetalhamentoAvaliacao(
        Long id,
        Integer nota,
        String comentario,
        Long idAgendamento,
        String nomeFuncionario
) {
    public DadosDetalhamentoAvaliacao(Avaliacao avaliacao) {
        this(
                avaliacao.getId(),
                avaliacao.getNota(),
                avaliacao.getComentario(),
                avaliacao.getAgendamento().getId(),
                avaliacao.getAgendamento().getFuncionario().getNome()
        );
    }
}
