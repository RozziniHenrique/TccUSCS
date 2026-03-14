package erp.Salao.controller;

import erp.Salao.domain.especialidade.dto.*;
import erp.Salao.service.EspecialidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("especialidades")
@PreAuthorize("hasAnyAuthority('ADMIN', 'GESTOR')")
public class EspecialidadeController {

  @Autowired
  private EspecialidadeService service;

  @PostMapping
  public ResponseEntity cadastrar(
    @RequestBody @Valid CadastrarEspecialidadeDTO dados,
    UriComponentsBuilder uriBuilder
  ) {
    var especialidade = service.cadastrar(dados);
    var uri = uriBuilder
      .path("/especialidades/{id}")
      .buildAndExpand(especialidade.getId())
      .toUri();
    return ResponseEntity.created(uri).body(
      new DetalharEspecialidadeDTO(especialidade)
    );
  }

  @GetMapping
  @PreAuthorize("permitAll()") // Qualquer um pode ver as especialidades oferecidas
  public ResponseEntity<Page<ListarEspecialidadeDTO>> listar(
    @RequestParam(required = false, defaultValue = "true") Boolean ativos,
    @PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao
  ) {
    var page = service.listar(ativos, paginacao);
    return ResponseEntity.ok(page);
  }

  @GetMapping("/{id}")
  @PreAuthorize("permitAll()")
  public ResponseEntity detalhar(@PathVariable Long id) {
    var especialidade = service.detalhar(id);
    return ResponseEntity.ok(new DetalharEspecialidadeDTO(especialidade));
  }

  @PutMapping
  public ResponseEntity atualizar(
    @RequestBody @Valid AtualizarEspecialidadeDTO dados
  ) {
    var especialidade = service.atualizar(dados);
    return ResponseEntity.ok(new DetalharEspecialidadeDTO(especialidade));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity excluir(@PathVariable Long id) {
    service.excluir(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/reativar")
  public ResponseEntity reativar(@PathVariable Long id) {
    service.reativar(id);
    return ResponseEntity.ok().build();
  }
}
