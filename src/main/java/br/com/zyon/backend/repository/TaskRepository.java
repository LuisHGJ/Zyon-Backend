package br.com.zyon.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zyon.backend.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

} 
