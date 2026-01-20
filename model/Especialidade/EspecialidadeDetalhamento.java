package uscs.STEFER.model.Especialidade;

public record EspecialidadeDetalhamento(Long id, String nome, String descricao) {
    public EspecialidadeDetalhamento(Especialidade especialidade) {
        this(especialidade.getId(), especialidade.getNome(), especialidade.getDescricao());
    }
}
