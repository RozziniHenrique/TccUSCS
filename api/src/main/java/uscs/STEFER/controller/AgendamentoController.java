package uscs.STEFER.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import uscs.STEFER.infra.ValidacaoException;
import uscs.STEFER.model.Agendamento.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/agendamentos")

public class AgendamentoController {

    @Autowired
    private AgendamentoService service;

    @Autowired
    private AgendamentoRepository repository;

    @PostMapping
    @Operation(summary = "Agenda uma nova consulta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta agendada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou violação de regra de negócio (ex: antecedência de 30min)")
    })
    @Transactional
    public ResponseEntity agendar (@RequestBody @Valid DadosAgendamento dados, UriComponentsBuilder uriBuilder){
        var dto = service.agendar(dados);
        var uri = uriBuilder.path("/agendamentos/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<AgendamentoDetalhamento>> listar(
            @PageableDefault(size = 10, sort = {"data"}) Pageable paginacao){
        var page = repository.findAllByMotivoCancelamentoIsNull(paginacao)
                .map(AgendamentoDetalhamento::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var agendamento = repository.findByIdAndMotivoCancelamentoIsNull(id)
                .orElseThrow(() -> new ValidacaoException("Agendamento não encontrado ou já cancelado!"));

        return ResponseEntity.ok(new AgendamentoDetalhamento(agendamento));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoAgendamento dados){
        service.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
