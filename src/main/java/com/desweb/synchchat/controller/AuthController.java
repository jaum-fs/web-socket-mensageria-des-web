package com.desweb.synchchat.controller;

import com.desweb.synchchat.model.Usuario;
import com.desweb.synchchat.repository.UserRepository;
import com.desweb.synchchat.service.JwtService;
import com.desweb.synchchat.util.TokenResponse;
import com.desweb.synchchat.util.UsuarioLogin;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository usuarioRepository;

    @PostMapping("login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody UsuarioLogin usuarioLogin,
                                               HttpServletResponse response) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioLogin.getNickname(), usuarioLogin.getSenha()));

        Usuario usuario = usuarioRepository.findByNickname(usuarioLogin.getNickname()).orElseThrow();

        String accessToken = jwtService.generateAccessToken(usuario);

        return new ResponseEntity<>(new TokenResponse(
                accessToken, usuario.getId(), usuario.getNickname(), usuario.getRole().name()), HttpStatus.OK);
    }
}
