package br.com.zyon.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.zyon.backend.entity.User;
import br.com.zyon.backend.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    };

    public User create(User user) {
        userRepository.save(user);
        return user;
    };

    public List<User> list() {
        Sort sort = Sort.by(Direction.DESC, "xp")
            .and(Sort.by(Direction.ASC, "id"));
        return userRepository.findAll(sort);
    };

    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
    
    
    public List<User> update(User user) {
        userRepository.save(user);
        return list();
    };

    public List<User> delete(Long id) {
        userRepository.deleteById(id);
        return list();
    };

    public Optional<User> authenticate(String email, String senha) {
        Optional<User> u = userRepository.findByEmail(email);
        return u.filter(user -> user.getSenha()!= null && user.getSenha().equals(senha));
    }
};