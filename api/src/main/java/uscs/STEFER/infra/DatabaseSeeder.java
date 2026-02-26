package uscs.STEFER.infra;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uscs.STEFER.model.Funcionario.Funcionario;
import uscs.STEFER.model.Funcionario.FuncionarioRepository;
import uscs.STEFER.model.Usuario.Usuario;
import uscs.STEFER.model.Usuario.UsuarioRepository;
import uscs.STEFER.model.Usuario.UsuarioRole;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional // Garante que ou cria os dois, ou não cria nenhum
    public void run(String... args) throws Exception {

        String emailAdmin = "admin@stefer.com";

        // 1. Verifica se o Admin já existe para não criar duplicado toda vez que rodar
        if (usuarioRepository.findByLogin(emailAdmin) == null) {

            System.out.println(">>> Seed: Criando usuário administrador padrão...");

            // 2. Criar o Perfil do Funcionário Admin
            // (Ajuste os campos de acordo com o seu construtor de Funcionario)
            var funcionarioAdmin = new Funcionario();
            funcionarioAdmin.setNome("Administrador Sistema");
            funcionarioAdmin.setEmail(emailAdmin);
            funcionarioAdmin.setCpf("00000000000");
            funcionarioAdmin.setTelefone("11999999999");
            funcionarioAdmin.setAtivo(true);

            funcionarioRepository.save(funcionarioAdmin);

            // 3. Criar o Usuário de Login vinculado a esse funcionário
            var usuarioAdmin = new Usuario(
                    null,
                    emailAdmin,
                    passwordEncoder.encode("admin123"),
                    UsuarioRole.ADMIN
            );

            usuarioRepository.save(usuarioAdmin);

            System.out.println(">>> Seed: Admin criado com sucesso! Login: " + emailAdmin + " | Senha: admin123");
        } else {
            System.out.println(">>> Seed: Admin já existe no banco. Pulando etapa.");
        }
    }
}
