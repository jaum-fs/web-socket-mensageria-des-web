package com.desweb.synchchat.service;

import com.desweb.synchchat.repository.UserRepository;
import com.desweb.synchchat.model.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UserRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository
                .findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new User(usuario.getNickname(), usuario.getSenha(), Collections.emptyList());
    }
}