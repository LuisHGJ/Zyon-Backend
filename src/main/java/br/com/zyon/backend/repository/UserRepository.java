package br.com.zyon.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.zyon.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u ORDER BY u.nivel DESC, u.xp DESC")
    List<User> findAllOrderedByNivelXp();

    List<User> findByNomeContainingIgnoreCase(String nome);
}
