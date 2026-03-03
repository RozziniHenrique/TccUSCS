package uscs.STEFER.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uscs.STEFER.domain.agendamento.AgendamentoRepository;
import uscs.STEFER.domain.dashboard.dto.dtoDashboard;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class DashboardService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public dtoDashboard obterDadosDashboard() {
        var hojeInicio = LocalDate.now().atStartOfDay();
        var hojeFim = LocalDate.now().atTime(23, 59, 59);

        var faturamentoBruto = agendamentoRepository.calcularFaturamentoTotal(hojeInicio, hojeFim);
        var faturamentoFinal = (faturamentoBruto != null) ? faturamentoBruto : BigDecimal.ZERO;

        return new dtoDashboard(
                faturamentoFinal,
                agendamentoRepository.findRankingFuncionarios(),
                agendamentoRepository.findPioresAvaliados(),
                agendamentoRepository.count()
        );
    }
}