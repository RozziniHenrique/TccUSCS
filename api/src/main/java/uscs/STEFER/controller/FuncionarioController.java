package uscs.STEFER.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uscs.STEFER.domain.especialidade.EspecialidadeRepository;
import uscs.STEFER.domain.funcionario.Funcionario;
import uscs.STEFER.domain.funcionario.FuncionarioRepository;
import uscs.STEFER.domain.funcionario.dto.dtoFuncionarioAtualizar;
import uscs.STEFER.domain.funcionario.dto.dtoFuncionarioCadastrar;
import uscs.STEFER.domain.funcionario.dto.dtoFuncionarioDetalhar;
import uscs.STEFER.domain.funcionario.dto.dtoFuncionarioListar;
import uscs.STEFER.service.FuncionarioService;

@RestController
@RequestMapping("funcionarios")

public class FuncionarioController {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private FuncionarioService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid dtoFuncionarioCadastrar dados) {
        var funcionario = service.cadastrar(dados);
        return ResponseEntity.ok(new dtoFuncionarioDetalhar(funcionario));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarFuncionario(@RequestBody @Valid dtoFuncionarioAtualizar dados) {
        var funcionario = service.atualizar(dados);
        return ResponseEntity.ok(new dtoFuncionarioDetalhar(funcionario));
    }

    @GetMapping
    public ResponseEntity<Page<dtoFuncionarioListar>> listarFuncionario(
            @RequestParam(required = false) Long idEspecialidade,
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {

        Page<Funcionario> page;

        if (idEspecialidade != null) {
            page = repository.findAllByAtivoTrueAndEspecialidadesId(idEspecialidade, paginacao);
        } else {
            page = repository.findAllByAtivoTrue(paginacao);
        }

        return ResponseEntity.ok(page.map(dtoFuncionarioListar::new));
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
        return ResponseEntity.ok(new dtoFuncionarioDetalhar(funcionario));
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