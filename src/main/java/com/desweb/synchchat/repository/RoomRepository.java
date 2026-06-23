package com.desweb.synchchat.repository;

import com.desweb.synchchat.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface RoomRepository extends JpaRepository<Room, UUID> {

    //Retorna uma lista com todas as salas pertencentes a um usuário
    List<Room> findByOwnerId(Long ownerId);
}