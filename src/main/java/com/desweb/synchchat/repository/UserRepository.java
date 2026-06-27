package com.desweb.synchchat.repository;

import com.desweb.synchchat.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByNickname(String nickname);
}