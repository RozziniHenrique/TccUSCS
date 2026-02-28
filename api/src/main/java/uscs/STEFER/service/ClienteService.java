package uscs.STEFER.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uscs.STEFER.domain.cliente.Cliente;
import uscs.STEFER.domain.cliente.ClienteRepository;
import uscs.STEFER.domain.cliente.dto.dtoClienteAtualizar;
import uscs.STEFER.domain.cliente.dto.dtoClienteCadastrar;
import uscs.STEFER.domain.usuario.Usuario;
import uscs.STEFER.domain.usuario.UsuarioRepository;
import uscs.STEFER.domain.usuario.UsuarioRole;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Cliente cadastrar(dtoClienteCadastrar dados) {

        var senhaSegura = passwordEncoder.encode(dados.senha());
        var usuario = new Usuario(null, dados.email(), senhaSegura, true, UsuarioRole.CLIENTE);
        usuarioRepository.save(usuario);

        var cliente = new Cliente(dados);
        cliente.setUsuario(usuario);

        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente atualizar(dtoClienteAtualizar dados) {
        var cliente = clienteRepository.getReferenceById(dados.id());
        cliente.atualizarCliente(dados);
        return cliente;
    }
}
