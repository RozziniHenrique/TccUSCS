package uscs.STEFER.model.Cliente;

import uscs.STEFER.model.endereco.Endereco;

public record ClienteDetalhamento(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco) {

    public ClienteDetalhamento(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getCpf(),
                cliente.getEndereco());
    }
}
