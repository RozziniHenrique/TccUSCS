package erp.Salao.domain.agendamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CancelarAgendamentoDTO(
  @NotNull Long idAgendamento,

  @NotBlank String motivo
) {}
