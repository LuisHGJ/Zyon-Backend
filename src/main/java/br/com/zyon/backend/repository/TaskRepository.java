package br.com.zyon.backend.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.zyon.backend.entity.User;
import br.com.zyon.backend.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}