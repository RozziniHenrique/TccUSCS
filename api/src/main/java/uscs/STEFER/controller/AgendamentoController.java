package uscs.STEFER.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import uscs.STEFER.domain.agendamento.AgendamentoRepository;
import uscs.STEFER.domain.agendamento.dto.*;
import uscs.STEFER.domain.avaliacao.Avaliacao;
import uscs.STEFER.domain.avaliacao.AvaliacaoRepository;
import uscs.STEFER.domain.avaliacao.dto.dtoAvaliacaoCadastrar;
import uscs.STEFER.domain.avaliacao.dto.dtoAvaliacaoDetalhar;
import uscs.STEFER.infra.exception.ValidacaoException;
import uscs.STEFER.service.AgendamentoService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")

public class AgendamentoController {

    @Autowired
    private AgendamentoService service;

    @Autowired
    private AgendamentoRepository repository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @PostMapping
    @Operation(summary = "Agenda uma nova consulta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta agendada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou violação de regra de negócio (ex: antecedência de 30min)")
    })
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid dtoAgendamentoCadastrar dados, UriComponentsBuilder uriBuilder) {
        var dto = service.agendar(dados);
        var uri = uriBuilder.path("/agendamentos/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }


    @GetMapping("/relatorio/estatisticas")
    public ResponseEntity<List<dtoAgendamentoRelatorioEspecialidade>> relatorioEspecialidades() {
        var dados = repository.contagemPorEspecialidade();
        return ResponseEntity.ok(dados);
    }

    @PostMapping("/{id}/avaliar")
    @Transactional
    public ResponseEntity avaliar(@PathVariable Long id, @RequestBody @Valid dtoAvaliacaoCadastrar dados) {

        var agendamento = repository.getReferenceById(id);

        if (avaliacaoRepository.existsByAgendamentoId(id)) {
            return ResponseEntity.badRequest().body("Este agendamento já foi avaliado!");
        }

        var avaliacao = new Avaliacao(dados, agendamento);
        avaliacaoRepository.save(avaliacao);

        return ResponseEntity.ok(new dtoAvaliacaoDetalhar(avaliacao));
    }

    @PutMapping("/finalizar")
    @Transactional
    public ResponseEntity finalizar(@RequestBody @Valid dtoAgendamentoFinalizar dados) {
        var agendamento = repository.getReferenceById(dados.id());
        agendamento.finalizar(dados.nota());

        return ResponseEntity.ok("Atendimento finalizado com sucesso! Faturamento atualizado.");
    }

    @GetMapping
    public ResponseEntity<Page<dtoAgendamentoDetalhar>> listar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(required = false) Long idFuncionario,
            @RequestParam(required = false) Long idCliente,
            @RequestParam(required = false) Long idEspecialidade,
            @PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {

        var page = repository.findAllComFiltros(data, idFuncionario, idCliente, idEspecialidade, paginacao)
                .map(dtoAgendamentoDetalhar::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var agendamento = repository.findByIdAndMotivoCancelamentoIsNull(id)
                .orElseThrow(() -> new ValidacaoException("Agendamento não encontrado ou já cancelado!"));

        return ResponseEntity.ok(new dtoAgendamentoDetalhar(agendamento));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid dtoAgendamentoCancelar dados) {
        service.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
