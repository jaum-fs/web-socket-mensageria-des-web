package com.desweb.synchchat.service;

import com.desweb.synchchat.model.Role;
import com.desweb.synchchat.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    // A chave secreta fornecida no arquivo .env foi gerada com o url abaixo:
    // https://generate-random.org/

    // A chave secreta deve ser armazenada fora do código fonte.
    // Vamos salvá-la em um arquivo .env, na raiz do projeto.
    // Este arquivo não deve ser copiado para o repositório do projeto, logo o seu
    // nome (.env) deve ser adicionado a .gitignore para não ser salvo no repositório.

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${jwt.security.accessTokenExpiration}")
    private String accessTokenExpiration;

    public String generateAccessToken(Usuario usuario) {
        final long tokenExpiration = Long.parseLong(accessTokenExpiration); // 2 horas
        return generateToken(usuario, tokenExpiration);
    }

    private String generateToken(Usuario usuario, long tokenExpiration) {
        return Jwts.builder()
                .subject(usuario.getId().toString())
                .claim("nick", usuario.getNickname())
                .claim("role", usuario.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration * 1000))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);
            boolean valido = claims.getExpiration().after(new Date());
            return valido;
        }
        catch(JwtException e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }

    public Role getRoleFromToken(String token) {
        return Role.valueOf(getClaims(token).get("role", String.class));
    }
}
