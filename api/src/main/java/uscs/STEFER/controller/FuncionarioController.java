package uscs.STEFER.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import uscs.STEFER.model.Especialidade.EspecialidadeRepository;
import uscs.STEFER.model.Funcionario.*;

import java.util.List;

@RestController
@RequestMapping("funcionarios")

public class FuncionarioController {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastroFuncionario(@RequestBody @Valid FuncionarioCadastro dados, UriComponentsBuilder uriBuilder) {
        var funcionario = new Funcionario(dados);
        var especialidades = especialidadeRepository.findAllById(dados.especialidadesIds());
        funcionario.getEspecialidades().addAll(especialidades);
        repository.save(funcionario);
        var uri = uriBuilder.path("/funcionarios/{id}").buildAndExpand(funcionario.getId()).toUri();
        return ResponseEntity.created(uri).body(new FuncionarioDetalhamento(funcionario));
    }

    @GetMapping
    public ResponseEntity<Page<FuncionarioLista>> listarFuncionario(
            @RequestParam(required = false) Long idEspecialidade,
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {

        Page<Funcionario> page;

        if (idEspecialidade != null) {
            page = repository.findAllByAtivoTrueAndEspecialidadesId(idEspecialidade, paginacao);
        } else {
            page = repository.findAllByAtivoTrue(paginacao);
        }

        return ResponseEntity.ok(page.map(FuncionarioLista::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarFuncionario(@RequestBody @Valid FuncionarioAtualizacao dados){
        var funcionario = repository.getReferenceById(dados.id());
        funcionario.atualizarFuncionario(dados);
        if (dados.especialidadesIds() != null) {
            var especialidades = especialidadeRepository.findAllById(dados.especialidadesIds());
            funcionario.atualizarEspecialidades(especialidades);
        }
        return ResponseEntity.ok(new FuncionarioDetalhamento(funcionario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirFuncionario(@PathVariable Long id) {
        var funcionario = repository.getReferenceById(id);
        funcionario.excluirFuncionario();

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reativar")
    @Transactional
    public ResponseEntity reativarFuncionario(@PathVariable Long id) {
        var funcionario = repository.getReferenceById(id);
        funcionario.reativarFuncionario();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity FuncionarioDetalhamento(@PathVariable Long id) {
        var funcionario = repository.getReferenceById(id);
        return ResponseEntity.ok(new FuncionarioDetalhamento(funcionario));
    }

    @DeleteMapping("/{idFuncionario}/{idEspecialidade}")
    @Transactional
    public ResponseEntity removerEspecialidade(
            @PathVariable Long idFuncionario,
            @PathVariable Long idEspecialidade) {
        var funcionario = repository.findById(idFuncionario)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
        var especialidade = especialidadeRepository.findById(idEspecialidade)
                .orElseThrow(() -> new EntityNotFoundException("Especialidade não encontrada"));
        funcionario.removerEspecialidades(java.util.List.of(especialidade));
        return ResponseEntity.noContent().build();
    }
}