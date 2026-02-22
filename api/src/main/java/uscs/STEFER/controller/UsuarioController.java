package uscs.STEFER.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uscs.STEFER.model.Usuario.DadosCadastroUsuario;
import uscs.STEFER.model.Usuario.Usuario;
import uscs.STEFER.model.Usuario.UsuarioRepository;
import uscs.STEFER.model.Usuario.UsuarioRole;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados) {
        var senhaSegura = passwordEncoder.encode(dados.senha());
        var usuario = new Usuario(null, dados.login(), senhaSegura, UsuarioRole.CLIENTE);
        repository.save(usuario);

        return ResponseEntity.ok().build();
    }
}
