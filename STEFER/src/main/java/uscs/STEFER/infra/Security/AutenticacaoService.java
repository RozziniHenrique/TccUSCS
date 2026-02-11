package uscs.STEFER.infra.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uscs.STEFER.model.Usuario.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = repository.findByLogin(username);
        System.out.println("Usuário encontrado: " + usuario.getUsername());
        System.out.println("Senha no banco: " + usuario.getPassword());
        return repository.findByLogin(username);
    }
}
