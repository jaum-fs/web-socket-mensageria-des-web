package com.desweb.synchchat.service;

import com.desweb.synchchat.dto.RoomDto;
import com.desweb.synchchat.mapper.RoomMapper;
import com.desweb.synchchat.model.Room;
import com.desweb.synchchat.model.Usuario;
import com.desweb.synchchat.repository.RoomRepository;
import com.desweb.synchchat.repository.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private RoomMapper roomMapper;
    
    // READ - Lista todas as salas
    public List<RoomDto> listarTodos() {
        return roomMapper.toRoomsDto(roomRepository.findAll());
    }

    // READ - Busca sala por ID
    public RoomDto buscarPorId(UUID id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala com id = " + id + " não encontrada."));
        return roomMapper.toRoomDto(room);
    }

    // READ - Busca salas por dono
    public List<RoomDto> buscarPorDono(Long ownerId) {
        return roomMapper.toRoomsDto(roomRepository.findByOwnerId(ownerId));
    }

    // CREATE - Cria uma sala pública
    public RoomDto criarSalaPublica(Long ownerId) {
        // Verifica se o owner existe
        val user = userRepository.findById(ownerId)
            .orElseThrow(() -> new RuntimeException("Usuário com id = " + ownerId + " não encontrado."));
        
        Room room = new Room(user);
        room.setPassword(null);
        return roomMapper.toRoomDto(roomRepository.save(room));
    }

    public RoomDto criarSalaPrivada(Long ownerId, String password) {
        val user = userRepository.findById(ownerId)
            .orElseThrow(() -> new RuntimeException("Usuário com id = " + ownerId + " não encontrado."));
        
        if (password == null || password.trim().isEmpty()) {
            throw new RuntimeException("Sala privada precisa de uma senha!");
        }
        
        Room room = new Room(user);
        room.setPassword(password);
        return roomMapper.toRoomDto(roomRepository.save(room));
    }

    // UPDATE - Adiciona usuário à sala
    public RoomDto adicionarUsuario(UUID roomId, Long userId, String password) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Sala com id = " + roomId + " não encontrada."));

        if (room.getPassword() != null && !room.getPassword().isEmpty()) {
            if (!room.getPassword().equals(password)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Senha incorreta.");
            }
        }

        Usuario usuario = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário com id = " + userId + " não encontrado."));

        if (room.getUsers() == null) {
            room.setUsers(new java.util.ArrayList<>());
        }

        boolean alreadyIn = room.getUsers().stream().anyMatch(u -> u.getId().equals(userId));
        if (!alreadyIn) {
            room.getUsers().add(usuario);
        }

        return roomMapper.toRoomDto(roomRepository.save(room));
    }

    // DELETE - Remove usuário da sala
    public RoomDto removerUsuario(UUID roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Sala com id = " + roomId + " não encontrada."));

        if (room.getUsers() != null) {
            room.getUsers().removeIf(u -> u.getId().equals(userId));
        }

        return roomMapper.toRoomDto(roomRepository.save(room));
    }

    // DELETE - Deleta a sala
    public void deletar(UUID id) {
        roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala com id = " + id + " não encontrada."));
        roomRepository.deleteById(id);
    }
}