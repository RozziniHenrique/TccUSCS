package uscs.STEFER.model.Cliente;


public record ClienteLista(Long id, String nome, String email, String cpf) {

    public ClienteLista(Cliente cliente){
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf());
    }
}
