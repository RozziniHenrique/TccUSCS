package erp.Salao.domain.avaliacao.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CadastrarAvaliacaoDTO(
  @NotNull @Min(1) @Max(5) Integer nota,
  String comentario
) {}
