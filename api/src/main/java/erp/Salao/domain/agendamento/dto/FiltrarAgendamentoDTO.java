package erp.Salao.domain.agendamento.dto;

import java.time.LocalDate;

public record FiltrarAgendamentoDTO(
  LocalDate data,
  Long idFuncionario,
  Long idCliente
) {}
