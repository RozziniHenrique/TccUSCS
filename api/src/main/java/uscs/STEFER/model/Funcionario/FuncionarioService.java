package uscs.STEFER.model.Funcionario;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uscs.STEFER.model.Especialidade.EspecialidadeRepository;
import uscs.STEFER.model.Usuario.Usuario;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository repository;
    @Autowired
    private EspecialidadeRepository especialidadeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Funcionario cadastrar(FuncionarioCadastro dados) {
        String senhaLimpa = dados.nome().substring(0, 3).toLowerCase() + dados.cpf().substring(0, 3);
        String senhaCriptografada = passwordEncoder.encode(senhaLimpa);

        var usuario = new Usuario(null, dados.email(), senhaCriptografada, dados.role());

        var funcionario = new Funcionario(dados);
        funcionario.setUsuario(usuario);

        var especialidades = especialidadeRepository.findAllById(dados.especialidadesIds());
        funcionario.getEspecialidades().addAll(especialidades);

        System.out.println(">>> Senha gerada para " + dados.nome() + ": " + senhaLimpa);

        return repository.save(funcionario);
    }

    @Transactional
    public Funcionario atualizar(FuncionarioAtualizacao dados) {
        var funcionario = repository.getReferenceById(dados.id());
        funcionario.atualizarFuncionario(dados);

        if (dados.especialidadesIds() != null) {
            var especialidades = especialidadeRepository.findAllById(dados.especialidadesIds());
            funcionario.atualizarEspecialidades(especialidades);
        }

        return funcionario;
    }
}
