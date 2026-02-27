package uscs.STEFER.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uscs.STEFER.model.Agendamento.AgendamentoRepository;
import uscs.STEFER.model.Usuario.UsuarioRepository;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Long>> getDashboard() {
        var resultados = repository.contarUsuariosPorRole();

        Map<String, Long> stats = resultados.stream()
                .collect(Collectors.toMap(
                        line -> line[0].toString(),
                        line -> (Long) line[1]
                ));

        return ResponseEntity.ok(stats);
    }
}
