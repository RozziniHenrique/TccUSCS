package uscs.STEFER.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uscs.STEFER.model.Agendamento.AgendamentoDetalhamento;
import uscs.STEFER.model.Agendamento.AgendamentoRepository;
import uscs.STEFER.model.Agendamento.AgendamentoService;
import uscs.STEFER.model.Agendamento.DadosAgendamento;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/agendamentos")

public class AgendamentoController {

    @Autowired
    private AgendamentoService service;

    @Autowired
    private AgendamentoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity agendar (@RequestBody @Valid DadosAgendamento dados){
        service.agendar(dados);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<AgendamentoDetalhamento>> listar(
            @PageableDefault(size = 10, sort = {"data"}) Pageable paginacao){
        var page = repository.findAll(paginacao)
                .map(AgendamentoDetalhamento::new);

        return ResponseEntity.ok(page);
    }
}

