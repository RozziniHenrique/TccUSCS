package uscs.STEFER.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uscs.STEFER.domain.especialidade.EspecialidadeRepository;
import uscs.STEFER.domain.funcionario.Funcionario;
import uscs.STEFER.domain.funcionario.FuncionarioRepository;
import uscs.STEFER.domain.funcionario.dto.*;
import uscs.STEFER.domain.usuario.Usuario;
import uscs.STEFER.domain.usuario.UsuarioRepository;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;
    @Autowired
    private EspecialidadeRepository especialidadeRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Funcionario cadastrar(dtoFuncionarioCadastrar dados) {
        // Lógica de geração de senha: 3 letras do nome + 3 números do CPF
        String senhaLimpa = dados.nome().substring(0, 3).toLowerCase() + dados.cpf().substring(0, 3);
        String senhaCriptografada = passwordEncoder.encode(senhaLimpa);

        var usuario = new Usuario(null, dados.email(), senhaCriptografada, true, dados.role());
        usuarioRepository.save(usuario);

        var funcionario = new Funcionario(dados);
        funcionario.setUsuario(usuario);

        if (dados.especialidadesIds() != null && !dados.especialidadesIds().isEmpty()) {
            var especialidades = especialidadeRepository.findAllById(dados.especialidadesIds());
            funcionario.getEspecialidades().addAll(especialidades);
        }

        System.out.println(">>> Senha gerada para " + dados.nome() + ": " + senhaLimpa);
        return repository.save(funcionario);
    }

    @Transactional
    public Funcionario atualizar(dtoFuncionarioAtualizar dados) {
        var funcionario = repository.getReferenceById(dados.id());
        funcionario.atualizarFuncionario(dados);

        if (dados.especialidadesIds() != null) {
            var especialidades = especialidadeRepository.findAllById(dados.especialidadesIds());
            funcionario.atualizarEspecialidades(especialidades);
        }
        return funcionario;
    }

    public Page<dtoFuncionarioListar> listar(Long idEspecialidade, Pageable paginacao) {
        if (idEspecialidade != null) {
            return repository.findAllByAtivoTrueAndEspecialidadesId(idEspecialidade, paginacao)
                    .map(dtoFuncionarioListar::new);
        }
        return repository.findAllByAtivoTrue(paginacao).map(dtoFuncionarioListar::new);
    }

    public Funcionario detalhar(Long id) {
        return repository.getReferenceById(id);
    }

    @Transactional
    public void excluir(Long id) {
        var funcionario = repository.getReferenceById(id);
        funcionario.excluirFuncionario();
        if (funcionario.getUsuario() != null) {
            funcionario.getUsuario().setAtivo(false);
        }
    }

    @Transactional
    public void reativar(Long id) {
        var funcionario = repository.getReferenceById(id);
        funcionario.reativarFuncionario();
        if (funcionario.getUsuario() != null) {
            funcionario.getUsuario().setAtivo(true);
        }
    }

    @Transactional
    public void removerEspecialidade(Long idFuncionario, Long idEspecialidade) {
        var funcionario = repository.findById(idFuncionario)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
        var especialidade = especialidadeRepository.findById(idEspecialidade)
                .orElseThrow(() -> new EntityNotFoundException("Especialidade não encontrada"));
        
        funcionario.removerEspecialidades(List.of(especialidade));
    }
}