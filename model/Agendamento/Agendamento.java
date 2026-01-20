package uscs.STEFER.model.Agendamento;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uscs.STEFER.model.Cliente.Cliente;
import uscs.STEFER.model.Especialidade.Especialidade;
import uscs.STEFER.model.Especialidade.EspecialidadeRepository;
import uscs.STEFER.model.Funcionario.Funcionario;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "agendamentos")
@Entity(name = "agendamentos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidade_id")
    private Especialidade especialidade;

    private LocalDateTime data;


}
