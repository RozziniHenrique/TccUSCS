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
import uscs.STEFER.domain.especialidade.Especialidade;
import uscs.STEFER.domain.especialidade.EspecialidadeRepository;
import uscs.STEFER.domain.especialidade.dto.dtoEspecialidadeAtualizar;
import uscs.STEFER.domain.especialidade.dto.dtoEspecialidadeCadastrar;
import uscs.STEFER.domain.especialidade.dto.dtoEspecialidadeDetalhar;
import uscs.STEFER.domain.especialidade.dto.dtoEspecialidadeListar;

@RestController
@RequestMapping("especialidades")

public class EspecialidadeController {

    @Autowired
    private EspecialidadeRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastro(@RequestBody @Valid dtoEspecialidadeCadastrar dados, UriComponentsBuilder uriBuilder) {
        var especialidade = new Especialidade(dados);
        repository.save(especialidade);
        var uri = uriBuilder.path("/especialidade/{id}").buildAndExpand(especialidade.getId()).toUri();
        return ResponseEntity.created(uri).body(new dtoEspecialidadeDetalhar(especialidade));
    }

    @GetMapping
    public ResponseEntity<Page<dtoEspecialidadeListar>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(dtoEspecialidadeListar::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid dtoEspecialidadeAtualizar dados) {
        var especialidade = repository.getReferenceById(dados.id());
        especialidade.atualizar(dados);
        return ResponseEntity.ok(new dtoEspecialidadeDetalhar(especialidade));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var especialidade = repository.getReferenceById(id);
        especialidade.excluir();

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reativar")
    @Transactional
    public ResponseEntity reativar(@PathVariable Long id) {
        var especialidade = repository.getReferenceById(id);
        especialidade.reativar();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity EspecialidadeDetalhamento(@PathVariable Long id) {
        var especialidade = repository.getReferenceById(id);
        return ResponseEntity.ok(new dtoEspecialidadeDetalhar(especialidade));
    }
}


