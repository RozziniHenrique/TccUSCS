package erp.Salao.domain.especialidade.dto;

import java.math.BigDecimal;

import erp.Salao.domain.especialidade.Especialidade;

public record DetalharEspecialidadeDTO(Long id, String nome, String descricao, BigDecimal preco) {
    public DetalharEspecialidadeDTO(Especialidade especialidade) {
        this(especialidade.getId(), especialidade.getNome(), especialidade.getDescricao(), especialidade.getPreco());
    }
}
