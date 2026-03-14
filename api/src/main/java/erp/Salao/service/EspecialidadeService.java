package erp.Salao.service;

import erp.Salao.domain.especialidade.Especialidade;
import erp.Salao.domain.especialidade.EspecialidadeRepository;
import erp.Salao.domain.especialidade.dto.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EspecialidadeService {

  @Autowired
  private EspecialidadeRepository repository;

  @Transactional
  public Especialidade cadastrar(CadastrarEspecialidadeDTO dados) {
    var especialidade = new Especialidade(dados);
    return repository.save(especialidade);
  }

  @Transactional
  public Especialidade atualizar(AtualizarEspecialidadeDTO dados) {
    var especialidade = repository.getReferenceById(dados.id());
    especialidade.atualizar(dados);
    return especialidade;
  }

  public Page<ListarEspecialidadeDTO> listar(
    Boolean ativos,
    Pageable paginacao
  ) {
    if (ativos != null && !ativos) {
      return repository
        .findAllByAtivoFalse(paginacao)
        .map(ListarEspecialidadeDTO::new);
    }
    return repository
      .findAllByAtivoTrue(paginacao)
      .map(ListarEspecialidadeDTO::new);
  }

  public Especialidade detalhar(Long id) {
    return repository.getReferenceById(id);
  }

  @Transactional
  public void excluir(Long id) {
    var especialidade = repository.getReferenceById(id);
    especialidade.excluir();
  }

  @Transactional
  public void reativar(Long id) {
    var especialidade = repository.getReferenceById(id);
    especialidade.reativar();
  }
}
