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
import uscs.STEFER.infra.ValidacaoException;
import uscs.STEFER.model.Agendamento.*;
import uscs.STEFER.model.Agendamento.relatorio.DadosRelatorioEspecialidade;
import uscs.STEFER.model.Avaliacao.Avaliacao;
import uscs.STEFER.model.Avaliacao.AvaliacaoRepository;
import uscs.STEFER.model.Avaliacao.DadosCadastroAvaliacao;
import uscs.STEFER.model.Avaliacao.DadosDetalhamentoAvaliacao;

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
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamento dados, UriComponentsBuilder uriBuilder) {
        var dto = service.agendar(dados);
        var uri = uriBuilder.path("/agendamentos/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }


    @GetMapping("/relatorio/estatisticas")
    public ResponseEntity<List<DadosRelatorioEspecialidade>> relatorioEspecialidades() {
        var dados = repository.contagemPorEspecialidade();
        return ResponseEntity.ok(dados);
    }

    @PostMapping("/{id}/avaliar")
    @Transactional
    public ResponseEntity avaliar(@PathVariable Long id, @RequestBody @Valid DadosCadastroAvaliacao dados) {

        var agendamento = repository.getReferenceById(id);

        if (avaliacaoRepository.existsByAgendamentoId(id)) {
            return ResponseEntity.badRequest().body("Este agendamento já foi avaliado!");
        }

        var avaliacao = new Avaliacao(dados, agendamento);
        avaliacaoRepository.save(avaliacao);

        return ResponseEntity.ok(new DadosDetalhamentoAvaliacao(avaliacao));
    }

    @PutMapping("/{id}/finalizar")
    @Transactional
    public ResponseEntity finalizar(@PathVariable Long id) {
        var agendamento = repository.getReferenceById(id);
        agendamento.setConcluido(true);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<AgendamentoDetalhamento>> listar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(required = false) Long idFuncionario,
            @RequestParam(required = false) Long idCliente,
            @RequestParam(required = false) Long idEspecialidade,
            @PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {

        var page = repository.findAllComFiltros(data, idFuncionario, idCliente, idEspecialidade, paginacao)
                .map(AgendamentoDetalhamento::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var agendamento = repository.findByIdAndMotivoCancelamentoIsNull(id)
                .orElseThrow(() -> new ValidacaoException("Agendamento não encontrado ou já cancelado!"));

        return ResponseEntity.ok(new AgendamentoDetalhamento(agendamento));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoAgendamento dados) {
        service.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
