package uscs.STEFER.model.Dashboard;

import java.math.BigDecimal;
import java.util.List;

public record DadosDashboard(
        BigDecimal faturamentoHoje,
        List<RankingFuncionario> ranking,
        List<AlertaQualidade> alertas,
        Long totalGeral
) {
}
