package uscs.STEFER.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uscs.STEFER.domain.especialidade.EspecialidadeRepository;
import uscs.STEFER.domain.funcionario.Funcionario;
import uscs.STEFER.domain.funcionario.FuncionarioRepository;
import uscs.STEFER.domain.funcionario.dto.dtoFuncionarioAtualizar;
import uscs.STEFER.domain.funcionario.dto.dtoFuncionarioCadastrar;
import uscs.STEFER.domain.usuario.Usuario;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository repository;
    @Autowired
    private EspecialidadeRepository especialidadeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Funcionario cadastrar(dtoFuncionarioCadastrar dados) {
        String senhaLimpa = dados.nome().substring(0, 3).toLowerCase() + dados.cpf().substring(0, 3);
        String senhaCriptografada = passwordEncoder.encode(senhaLimpa);

        var usuario = new Usuario(null, dados.email(), senhaCriptografada, dados.role());

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
}
