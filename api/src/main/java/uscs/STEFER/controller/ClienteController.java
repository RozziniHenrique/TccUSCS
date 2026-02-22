package uscs.STEFER.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import uscs.STEFER.model.Cliente.*;

@RestController
@RequestMapping("clientes")

public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ClienteService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastroCliente(@RequestBody @Valid ClienteCadastro dados, UriComponentsBuilder uriBuilder) {
        var cliente = service.cadastrar(dados);
        var uri = uriBuilder.path("/clientes/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(new ClienteDetalhamento(cliente));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarCliente(@RequestBody @Valid ClienteAtualizacao dados) {
        var cliente = service.atualizar(dados);
        return ResponseEntity.ok(new ClienteDetalhamento(cliente));
    }

    @GetMapping
    public ResponseEntity<Page<ClienteLista>> listarCliente(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(ClienteLista::new);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirCliente(@PathVariable Long id) {
        var cliente = repository.getReferenceById(id);
        cliente.excluirCliente();

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
    public ResponseEntity ClienteDetalhamento(@PathVariable Long id) {
        var cliente = repository.getReferenceById(id);
        return ResponseEntity.ok(new ClienteDetalhamento(cliente));
    }
}


