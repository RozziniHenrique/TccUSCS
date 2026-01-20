package uscs.STEFER.model.Especialidade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uscs.STEFER.model.Funcionario.Funcionario;

import java.util.HashSet;
import java.util.Set;

@Table(name = "especialidades")
@Entity(name = "Especialidade")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    private String descricao;

    private Boolean ativo;

    @ManyToMany(mappedBy = "especialidades")
    @JsonIgnore
    private Set<Funcionario> funcionarios = new HashSet<>();

    public Especialidade(EspecialidadeCadastro dados) {
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.ativo = true;
    }

    public void atualizar(@Valid EspecialidadeAtualizacao dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.descricao() != null) {
            this.descricao = dados.descricao();
        }
    }

    public void excluir() {
        this.ativo = false;
    }

    public void reativar() {
        this.ativo = true;
    }
}
