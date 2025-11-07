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

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(User user) {
        user.setSenha(passwordEncoder.encode(user.getSenha())); // 🔥 ESSENCIAL
        return userRepository.save(user);
    }


    public List<User> list() {
        Sort sort = Sort.by(Direction.DESC, "xp")
            .and(Sort.by(Direction.ASC, "id"));
        return userRepository.findAll(sort);
    };

    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
    
    public User update(User user) {
        return userRepository.save(user);
    }

    public List<User> delete(Long id) {
        userRepository.deleteById(id);
        return list();
    };

    // Autenticação de usuário
    public Optional<User> authenticate(String email, String senha) {
        Optional<User> u = userRepository.findByEmail(email);
        return u.filter(user -> user.getSenha()!= null && user.getSenha().equals(senha));
    }

    // Pesquisa de usuário por email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Ranking de usuários
    public List<User> findAllOrderedByNivelXp() {
        return userRepository.findAll(Sort.by(Direction.DESC, "xp"));
    }

    public void markUserAsPaid(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        user.setPaid(true);
        userRepository.save(user);
    }
};