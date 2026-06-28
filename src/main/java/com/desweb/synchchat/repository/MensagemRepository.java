package com.desweb.synchchat.repository;

import com.desweb.synchchat.model.Mensagem;
import com.desweb.synchchat.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

}