package br.com.zyon.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zyon.backend.entity.Task;
import br.com.zyon.backend.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    };

    @PostMapping
    public Task create(@RequestBody Task task) {
        return taskService.create(task); 
    }

    @GetMapping
    List<Task> list() {
        return taskService.list();
    };

    @PutMapping
    List<Task> update(@RequestBody Task task) {
        return taskService.update(task);
    };

    @DeleteMapping("{id}")
    List<Task> delete(@PathVariable("id") Long id) {
        return taskService.delete(id);
    };
};