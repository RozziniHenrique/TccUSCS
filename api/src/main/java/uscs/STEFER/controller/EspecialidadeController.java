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
import uscs.STEFER.model.Especialidade.*;

@RestController
@RequestMapping("especialidades")

public class EspecialidadeController {

    @Autowired
    private EspecialidadeRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastro(@RequestBody @Valid EspecialidadeCadastro dados, UriComponentsBuilder uriBuilder){
        var especialidade = new Especialidade(dados);
        repository.save(especialidade);
        var uri = uriBuilder.path("/especialidade/{id}").buildAndExpand(especialidade.getId()).toUri();
        return ResponseEntity.created(uri).body(new EspecialidadeDetalhamento(especialidade));
    }

    @GetMapping
    public ResponseEntity <Page<EspecialidadeLista>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        var page = repository.findAllByAtivoTrue(paginacao).map(EspecialidadeLista::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid EspecialidadeAtualizacao dados){
        var especialidade = repository.getReferenceById(dados.id());
        especialidade.atualizar(dados);
        return ResponseEntity.ok(new EspecialidadeDetalhamento(especialidade));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var especialidade = repository.getReferenceById(id);
        especialidade.excluir();

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reativar")
    @Transactional
    public ResponseEntity reativar(@PathVariable Long id){
        var especialidade = repository.getReferenceById(id);
        especialidade.reativar();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity EspecialidadeDetalhamento(@PathVariable Long id){
        var especialidade = repository.getReferenceById(id);
        return ResponseEntity.ok(new EspecialidadeDetalhamento(especialidade));
    }
}


