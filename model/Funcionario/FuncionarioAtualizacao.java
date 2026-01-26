package uscs.STEFER.model.Funcionario;

import jakarta.validation.constraints.NotNull;
import uscs.STEFER.model.endereco.dadoEndereco;
import java.util.List;

public record FuncionarioAtualizacao(
        @NotNull
        Long id,
        String nome,
        String telefone,
        dadoEndereco endereco,
        List<Long> especialidadesIds
) {
}
