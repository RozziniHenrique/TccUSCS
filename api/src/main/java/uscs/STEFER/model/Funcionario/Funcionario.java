package uscs.STEFER.model.Funcionario;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import uscs.STEFER.model.Especialidade.Especialidade;
import uscs.STEFER.model.Usuario.Usuario;
import uscs.STEFER.model.endereco.Endereco;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "funcionarios")
@Entity(name = "funcionarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    @OneToOne(cascade = CascadeType.ALL) // Se deletar o func, deleta o login
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
            name = "funcionario_especialidade",
            joinColumns = @JoinColumn(name = "funcionario_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidade_id")
    )
    private Set<Especialidade> especialidades = new HashSet<>();

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Funcionario(FuncionarioCadastro dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
        this.especialidades = new HashSet<>();
    }

    public void atualizarEspecialidades(List<Especialidade> novasEspecialidades) {
        if (novasEspecialidades != null) {
            novasEspecialidades.forEach(nova -> {
                if (!this.especialidades.contains(nova)) {
                    this.especialidades.add(nova);
                }
            });
        }
    }

    public void removerEspecialidades(List<Especialidade> antigasEspecialidades) {
        if (antigasEspecialidades != null) {
            antigasEspecialidades.forEach(especialidade -> {
                this.especialidades.remove(especialidade);
            });
        }
    }

    public void atualizarFuncionario(@Valid FuncionarioAtualizacao dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarEndereco(dados.endereco());
        }
    }

    public void excluirFuncionario() {
        this.ativo = false;
    }

    public void reativarFuncionario() {
        this.ativo = true;
    }
}

