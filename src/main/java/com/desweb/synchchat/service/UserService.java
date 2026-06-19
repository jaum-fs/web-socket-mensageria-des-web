package com.desweb.synchchat.service;

import com.desweb.synchchat.model.User;
import com.desweb.synchchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // READ - Listar todos os usuários
    public List<User> listarTodos() {
        return userRepository.findAll();
    }

    // READ - Buscar por ID
    public User buscarPorId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário com id = " + id + " não encontrado."));
    }

    // CREATE - Cadastrar usuário com validação de nickname único
    public User cadastrar(User user) {
        userRepository.findByNickname(user.getNickname())
                .ifPresent(u -> { 
                    throw new RuntimeException("Este nickname '" + user.getNickname() + "' já está sendo usado no chat!"); 
                });
        return userRepository.save(user);
    }

    // UPDATE - Alterar dados
    public User atualizar(User user) {
        // Garante que o usuário existe antes de tentar alterar
        buscarPorId(user.getId());
        return userRepository.save(user);
    }

    // DELETE - Tenta recuperar antes para validar se existe, depois deleta
    public void deletar(Long id) {
        buscarPorId(id); // Se não achar, já joga a exceção aqui e para a execução
        userRepository.deleteById(id);
    }
}