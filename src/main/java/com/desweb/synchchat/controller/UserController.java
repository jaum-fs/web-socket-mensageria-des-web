package com.desweb.synchchat.controller;

import com.desweb.synchchat.model.Usuario;
import com.desweb.synchchat.service.UserService;
import com.desweb.synchchat.util.InfoUserResponse;
import com.desweb.synchchat.util.UsuarioCreate;
import jakarta.validation.Valid;
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
    public List<Usuario> listarTodos() {
        return userService.listarTodos();
    }
 
    @GetMapping("{idUsuario}")
    public Usuario buscarPorId(@PathVariable("idUsuario") Long id) {
        return userService.buscarPorId(id);
    }
 
    @PostMapping
    public InfoUserResponse cadastrar(@RequestBody @Valid UsuarioCreate usuario) {
        return userService.cadastrar(usuario);
    }
 
    @PutMapping
    public Usuario atualizar(@RequestBody Usuario usuario) {
        return userService.atualizar(usuario);
    }

    @DeleteMapping("{idUsuario}")
    public void deletar(@PathVariable("idUsuario") Long id) {
        userService.deletar(id);
    }
}