package br.com.zyon.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zyon.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

} 
