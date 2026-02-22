package uscs.STEFER.model.Cliente;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uscs.STEFER.model.Usuario.Usuario;
import uscs.STEFER.model.Usuario.UsuarioRepository;
import uscs.STEFER.model.Usuario.UsuarioRole;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Cliente cadastrar(ClienteCadastro dados) {

        var senhaSegura = passwordEncoder.encode("mudar123");
        var usuario = new Usuario(null, dados.email(), senhaSegura, UsuarioRole.CLIENTE);

        var cliente = new Cliente(dados);
        cliente.setUsuario(usuario);

        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente atualizar(ClienteAtualizacao dados) {
        var cliente = clienteRepository.getReferenceById(dados.id());
        cliente.atualizarCliente(dados);
        return cliente;
    }
}
