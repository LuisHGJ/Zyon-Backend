package br.com.zyon.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> listarTasks() {
        return taskService.listarTasks();
    }

    @PostMapping
    public Task criarTask(@RequestBody Task task) {
        return taskService.criarTask(task);
    }

    @PutMapping("/{id}")
    public Task editarTask(@PathVariable Long id, @RequestBody Task novaTask) {
        return taskService.editarTask(id, novaTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTask(@PathVariable Long id) {
        taskService.deletarTask(id);
        return ResponseEntity.noContent().build();
    }
}