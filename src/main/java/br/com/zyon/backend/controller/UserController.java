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

import br.com.zyon.backend.entity.User;
import br.com.zyon.backend.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    };

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user); 
    }

    @GetMapping
    public List<User> list() {
        return userService.list();
    };

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping
    public List<User> update(@RequestBody User user) {
        return userService.update(user);
    };

    @DeleteMapping("{id}")
    List<User> delete(@PathVariable("id") Long id) {
        return userService.delete(id);
    };
};