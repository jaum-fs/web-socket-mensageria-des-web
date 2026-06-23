package com.desweb.synchchat.service;

import com.desweb.synchchat.dto.RoomDto;
import com.desweb.synchchat.mapper.RoomMapper;
import com.desweb.synchchat.model.Room;
import com.desweb.synchchat.model.User;
import com.desweb.synchchat.repository.RoomRepository;
import com.desweb.synchchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    
    // Atualiza a mensagem, a adiciona no histórico da sala
    public RoomDto updateMsg(UUID id, String newMessage) {
        Room room = roomRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
        
        // Adiciona ao histórico
        List<String> historico = room.getHistorico();
        // Nunca será true
        if (historico == null) {
            historico = new java.util.ArrayList<>();
            room.setHistorico(historico);
        }
        historico.add(newMessage);
        
        return roomMapper.toRoomDto(roomRepository.save(room));
    }
    
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
        userRepository.findById(ownerId)
            .orElseThrow(() -> new RuntimeException("Usuário com id = " + ownerId + " não encontrado."));
        
        Room room = new Room(ownerId);
        room.setPassword(null);
        return roomMapper.toRoomDto(roomRepository.save(room));
    }

    // CREATE - Cria sala privada com senha
    public RoomDto criarSalaPrivada(Long ownerId, String password) {
        // Verifica se o owner existe
        userRepository.findById(ownerId)
            .orElseThrow(() -> new RuntimeException("Usuário com id = " + ownerId + " não encontrado."));
        
        if (password == null || password.trim().isEmpty()) {
            throw new RuntimeException("Sala privada precisa de uma senha!");
        }
        
        Room room = new Room(ownerId);
        room.setPassword(password);
        return roomMapper.toRoomDto(roomRepository.save(room));
    }

    // UPDATE - Adiciona usuário à sala
    public RoomDto adicionarUsuario(UUID roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Sala com id = " + roomId + " não encontrada."));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário com id = " + userId + " não encontrado."));
        
        // Verifica se o usuário já está na sala
        if (room.getUsers() == null) {
            room.setUsers(new java.util.ArrayList<>());
        }
        
        if (!room.getUsers().contains(user)) {
            room.getUsers().add(user);
        }
        
        return roomMapper.toRoomDto(roomRepository.save(room));
    }

    // DELETE - Remove usuário da sala
    public RoomDto removerUsuario(UUID roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Sala com id = " + roomId + " não encontrada."));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário com id = " + userId + " não encontrado."));
        
        if (room.getUsers() != null) {
            room.getUsers().remove(user);
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