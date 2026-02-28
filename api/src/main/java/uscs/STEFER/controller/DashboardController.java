package uscs.STEFER.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uscs.STEFER.domain.agendamento.AgendamentoRepository;
import uscs.STEFER.domain.dashboard.dto.dtoDashboard;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @GetMapping
    public ResponseEntity obterDadosDashboard() {
        var hojeInicio = LocalDate.now().atStartOfDay();
        var hojeFim = LocalDate.now().atTime(23, 59, 59);

        var faturamentoBruto = agendamentoRepository.calcularFaturamentoTotal(hojeInicio, hojeFim);

        var faturamentoFinal = (faturamentoBruto != null) ? faturamentoBruto : BigDecimal.ZERO;

        var dados = new dtoDashboard(
                faturamentoFinal,
                agendamentoRepository.findRankingFuncionarios(),
                agendamentoRepository.findPioresAvaliados(),
                agendamentoRepository.count()
        );

        return ResponseEntity.ok(dados);
    }
}
