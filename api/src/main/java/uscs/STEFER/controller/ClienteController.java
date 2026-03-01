package uscs.STEFER.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uscs.STEFER.domain.cliente.ClienteRepository;
import uscs.STEFER.domain.cliente.dto.dtoClienteAtualizar;
import uscs.STEFER.domain.cliente.dto.dtoClienteCadastrar;
import uscs.STEFER.domain.cliente.dto.dtoClienteDetalhar;
import uscs.STEFER.domain.cliente.dto.dtoClienteListar;
import uscs.STEFER.service.ClienteService;

@RestController
@RequestMapping("clientes")
@PreAuthorize("hasAnyAuthority('ADMIN', 'GESTOR')")

public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ClienteService service;

    @PostMapping
    @Transactional
    @PreAuthorize("permitAll()")
    public ResponseEntity cadastrar(@RequestBody @Valid dtoClienteCadastrar dados) {
        var cliente = service.cadastrar(dados);
        return ResponseEntity.ok(new dtoClienteDetalhar(cliente));
    }

    @PutMapping
    @Transactional
    @PreAuthorize("hasAnyAuthority('ADMIN', 'GESTOR', 'CLIENTE')")
    public ResponseEntity atualizarCliente(@RequestBody @Valid dtoClienteAtualizar dados) {
        var cliente = service.atualizar(dados);
        return ResponseEntity.ok(new dtoClienteDetalhar(cliente));
    }

    @GetMapping
    public ResponseEntity<Page<dtoClienteListar>> listarCliente(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(dtoClienteListar::new);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirCliente(@PathVariable Long id) {
        var cliente = repository.getReferenceById(id);
        cliente.excluirCliente();

        if (cliente.getUsuario() != null) {
            cliente.getUsuario().setAtivo(false);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reativar")
    @Transactional
    public ResponseEntity reativarCliente(@PathVariable Long id) {
        var cliente = repository.getReferenceById(id);
        cliente.reativarCliente();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'GESTOR', 'CLIENTE')")
    public ResponseEntity ClienteDetalhamento(@PathVariable Long id) {
        var cliente = repository.getReferenceById(id);
        return ResponseEntity.ok(new dtoClienteDetalhar(cliente));
    }
}