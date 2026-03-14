package erp.Salao.domain.dashboard.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface SomarGastoClienteDTO {
    String getNome();
    BigDecimal getTotalGasto();
    LocalDateTime getUltimaVisita();
}
