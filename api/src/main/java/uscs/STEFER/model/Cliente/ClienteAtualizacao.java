package uscs.STEFER.model.Cliente;

import jakarta.validation.constraints.NotNull;
import uscs.STEFER.model.endereco.dadoEndereco;

public record ClienteAtualizacao(

        @NotNull
        Long id,
        String nome,
        String telefone,
        dadoEndereco endereco) {
}
