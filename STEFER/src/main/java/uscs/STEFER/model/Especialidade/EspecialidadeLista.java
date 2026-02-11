package uscs.STEFER.model.Especialidade;

public record EspecialidadeLista(Long id, String nome, String descricao) {

    public EspecialidadeLista(Especialidade especialidade){
        this(especialidade.getId(), especialidade.getNome(), especialidade.getDescricao());
    }
}