package erp.Salao.domain.cliente.dto;

import erp.Salao.domain.endereco.CadastrarEnderecoDTO;
import jakarta.validation.constraints.NotNull;

public record AtualizarClienteDTO(

        @NotNull
        Long id,
        String nome,
        String telefone,
        CadastrarEnderecoDTO endereco) {
}
