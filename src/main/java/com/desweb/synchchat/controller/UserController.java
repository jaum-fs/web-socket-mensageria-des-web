package com.desweb.synchchat.controller;

import com.desweb.synchchat.model.User;
import com.desweb.synchchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios") // URL base: http://localhost:8080/usuarios
@CrossOrigin(origins = "*") // Importante para o Lucas conseguir conectar o Front-end depois
public class UserController {

    @Autowired
    private UserService userService;

    //LISTAR TODOS
    @GetMapping
    public List<User> listarTodos() {
        return userService.listarTodos();
    }
 
    @GetMapping("{idUsuario}")
    public User buscarPorId(@PathVariable("idUsuario") Long id) {
        return userService.buscarPorId(id);
    }
 
    @PostMapping
    public User cadastrar(@RequestBody User user) {
        return userService.cadastrar(user);
    }
 
    @PutMapping
    public User atualizar(@RequestBody User user) {
        return userService.atualizar(user);
    }

    @DeleteMapping("{idUsuario}")
    public void deletar(@PathVariable("idUsuario") Long id) {
        userService.deletar(id);
    }
}