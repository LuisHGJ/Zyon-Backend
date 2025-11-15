package br.com.zyon.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zyon.backend.entity.Task;
import br.com.zyon.backend.entity.User;
import br.com.zyon.backend.repository.TaskRepository;
import br.com.zyon.backend.repository.UserRepository;
import br.com.zyon.backend.security.JwtUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class TaskService {

    private static final Short XP_FIXO_POR_TASK = 1000;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService; 

    @Autowired
    private JwtUtil jwtUtil;

    private User getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public List<Task> listarTasks() {
        User user = getAuthenticatedUser();
        return taskRepository.findByUser(user);
    }

    public Task criarTask(Task task) {
        User user = getAuthenticatedUser();
        
        if (task.getConcluido() == null) {
            task.setConcluido(false);
        }
        
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Task editarTask(Long id, Task novaTask) {
        User user = getAuthenticatedUser();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada"));

        if (!task.getUser().equals(user)) {
            throw new RuntimeException("Você não tem permissão para editar esta task");
        }

        // Atualiza os campos existentes
        task.setNome(novaTask.getNome());
        task.setDescricao(novaTask.getDescricao());
        task.setDificuldade(novaTask.getDificuldade());
        task.setPrioridade(novaTask.getPrioridade());
        task.setCicloTempo(novaTask.getCicloTempo());
        task.setConcluido(novaTask.getConcluido());
        task.setRecompensaXp(novaTask.getRecompensaXp());
        task.setDataAgendada(novaTask.getDataAgendada());

        return taskRepository.save(task);
    }

    public void deletarTask(Long id) {
        User user = getAuthenticatedUser();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada"));

        if (!task.getUser().equals(user)) {
            throw new RuntimeException("Você não tem permissão para deletar esta task");
        }

        taskRepository.delete(task);
    }

    public User concluirTask(Long id) {
        User user = getAuthenticatedUser();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada"));

        if (!task.getUser().equals(user)) {
            throw new RuntimeException("Você não tem permissão para concluir esta task");
        }
        
        if (task.getConcluido() != null && task.getConcluido()) {
            return user; 
        }

        if (Boolean.TRUE.equals(task.getConcluido())) {
            return userService.findById(user.getId()); 
        }
        task.setConcluido(true);
        taskRepository.save(task);

        Short xpGanho = XP_FIXO_POR_TASK;

        return userService.addXpAndCheckLevelUp(user, xpGanho);
    }
}
