package erp.Salao.domain.cliente.dto;


import erp.Salao.domain.cliente.Cliente;

public record ListarClienteDTO(Long id, String nome, String email, String cpf) {
    
    public ListarClienteDTO(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf());
    }
}
