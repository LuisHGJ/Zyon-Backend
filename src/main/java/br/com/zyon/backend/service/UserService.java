package br.com.zyon.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.zyon.backend.entity.User;
import br.com.zyon.backend.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    private static final long XP_BASE_POR_NIVEL = 1000;
    private static final short NIVEL_MAXIMO = 10;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(User user) {
        user.setSenha(passwordEncoder.encode(user.getSenha()));

        recalcularNivelTituloEstacao(user);
        return userRepository.save(user);
    }

    // NOVO MÉTODO: Busca de usuários por parte do nome
    public List<User> findByNomePartial(String nome) {
        return userRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<User> list() {
        Sort sort = Sort.by(Direction.DESC, "xp")
            .and(Sort.by(Direction.ASC, "id"));
        return userRepository.findAll(sort);
    };

    public User findById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        boolean changed = recalcularNivelTituloEstacao(user);
        if (changed) {
            user = userRepository.save(user);
        }
        
        return user;

    }
    
    public User update(User user) {
        recalcularNivelTituloEstacao(user);
        return userRepository.save(user);
    }

    public List<User> delete(Long id) {
        userRepository.deleteById(id);
        return list();
    };

    public Optional<User> authenticate(String email, String senha) {
        Optional<User> u = userRepository.findByEmail(email);
        
        Optional<User> authenticatedUser = u.filter(user -> 
            user.getSenha() != null && passwordEncoder.matches(senha, user.getSenha())
        );

        authenticatedUser.ifPresent(this::recalcularNivelTituloEstacao);

        return authenticatedUser;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAllOrderedByNivelXp() {
        return userRepository.findAll(Sort.by(Direction.DESC, "xp"));
    }

    public void markUserAsPaid(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        user.setPaid(true);
        userRepository.save(user);
    }

    private boolean recalcularNivelTituloEstacao(User user) {
        long xpAtual = user.getXp() != null ? user.getXp() : 0;
        
        short novoNivel = (short) Math.min(NIVEL_MAXIMO, (xpAtual / XP_BASE_POR_NIVEL) + 1);
        
        boolean needsUpdate = user.getNivel() == null || novoNivel != user.getNivel() || 
                              user.getEstacaoImagem() == null || user.getEstacaoImagem().isEmpty();

        if (needsUpdate) {
            user.setNivel(novoNivel);
            
            String titulo;
            String estacaoImagem;
            
            switch (novoNivel) {
                case 1:
                    titulo = "Cadete";
                    estacaoImagem = "nivel1.gif";
                    break;
                case 2:
                    titulo = "Tenente";
                    estacaoImagem = "nivel2.gif";
                    break;
                case 3:
                default:
                    titulo = "Almirante";
                    estacaoImagem = "nivel3.gif";
                    break;
            }
            user.setTitulo(titulo);
            user.setEstacaoImagem(estacaoImagem);
        }
        
        return needsUpdate;
    }

    public User addXpAndCheckLevelUp(User user, short xpGanho) {
        long xpAtual = user.getXp() != null ? user.getXp() : 0;
        long novoXp = xpAtual + xpGanho;
        
        user.setXp(novoXp);
        
        recalcularNivelTituloEstacao(user);
        return userRepository.save(user);
    }
}