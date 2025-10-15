package br.com.zyon.backend.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.zyon.backend.entity.Task;
import br.com.zyon.backend.repository.TaskRepository;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    };

    public Task create(Task task) {
        taskRepository.save(task);
        return task;
    };

    public List<Task> list() {
        Sort sort = Sort.by(Direction.DESC, "prioridade")
            .and(Sort.by(Direction.ASC, "id"));
        return taskRepository.findAll(sort);
    };

    public List<Task> update(Task task) {
        taskRepository.save(task);
        return list();
    };

    public List<Task> delete(Long id) {
        taskRepository.deleteById(id);
        return list();
    };
};