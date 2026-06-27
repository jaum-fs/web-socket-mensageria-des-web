package com.desweb.synchchat.service;

import com.desweb.synchchat.util.UsuarioCreate;
import com.desweb.synchchat.model.Role;
import com.desweb.synchchat.model.Usuario;
import com.desweb.synchchat.repository.UserRepository;
import com.desweb.synchchat.util.InfoUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // READ - Listar todos os usuários
    public List<Usuario> listarTodos() {
        return userRepository.findAll();
    }

    // READ - Buscar por ID
    public Usuario buscarPorId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário com id = " + id + " não encontrado."));
    }

    public Usuario buscarPorNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Usuário com id = " + nickname + " não encontrado."));
    }

    // UPDATE - Alterar dados
    public Usuario atualizar(Usuario usuario) {
        // Garante que o usuário existe antes de tentar alterar
        buscarPorId(usuario.getId());
        return userRepository.save(usuario);
    }

    // DELETE - Tenta recuperar antes para validar se existe, depois deleta
    public void deletar(Long id) {
        buscarPorId(id); // Se não achar, já joga a exceção aqui e para a execução
        userRepository.deleteById(id);
    }

    public InfoUserResponse cadastrar(UsuarioCreate usuarioCreate) {
        Usuario usuarioCadastrado = userRepository
                .findByNickname(usuarioCreate.getNickname())
                .orElse(null);
        if (usuarioCadastrado == null) {
            Usuario usuario = new Usuario(
                    usuarioCreate.getNickname(),
                    passwordEncoder.encode(usuarioCreate.getSenha()),
                    Role.USER);
            userRepository.save(usuario);

            return new InfoUserResponse(true, false, "Usuário com %s cadastrado com sucesso!".formatted(usuarioCreate.getNickname()));
        }
        else {
            return new InfoUserResponse(false, true, "Nickname já existe, tente com outro");
        }
    }
}