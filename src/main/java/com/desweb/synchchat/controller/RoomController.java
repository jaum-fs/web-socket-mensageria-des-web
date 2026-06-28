package com.desweb.synchchat.controller;

import com.desweb.synchchat.dto.RoomDto;
import com.desweb.synchchat.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/salas") // URL base: http://localhost:8080/salas
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    private RoomService roomService;


    // Lista todas as salas
    @GetMapping
    public ResponseEntity<List<RoomDto>> listarTodos() {
        List<RoomDto> rooms = roomService.listarTodos();
        return ResponseEntity.ok(rooms);
    }

    // Busca sala por id
    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> buscarPorId(@PathVariable UUID id) {
        RoomDto room = roomService.buscarPorId(id);
        return ResponseEntity.ok(room);
    }

    // Busca salas pelo dono
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<RoomDto>> buscarPorDono(@PathVariable Long ownerId) {
        List<RoomDto> rooms = roomService.buscarPorDono(ownerId);
        return ResponseEntity.ok(rooms);
    }

    // Cria uma sala pública
    @PostMapping("/publica/{ownerId}")
    public ResponseEntity<RoomDto> criarSalaPublica(@PathVariable Long ownerId) {
        RoomDto room = roomService.criarSalaPublica(ownerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    // Cria uma sala privada
    @PostMapping("/privada/{ownerId}")
    public ResponseEntity<RoomDto> criarSalaPrivada(
            @PathVariable Long ownerId,
            @RequestParam String password) {
        RoomDto room = roomService.criarSalaPrivada(ownerId, password);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    // Adiciona o usuário à sala
    @PutMapping("/{roomId}/usuario/{userId}")
    public ResponseEntity<RoomDto> adicionarUsuario(
            @PathVariable UUID roomId,
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "") String password) {
        RoomDto room = roomService.adicionarUsuario(roomId, userId, password);
        return ResponseEntity.ok(room);
    }

    // Remove o usuario da sala
    @DeleteMapping("/{roomId}/usuario/{userId}")
    public ResponseEntity<RoomDto> removerUsuario(
            @PathVariable UUID roomId,
            @PathVariable Long userId) {
        RoomDto room = roomService.removerUsuario(roomId, userId);
        return ResponseEntity.ok(room);
    }

    // Deleta a sala
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        roomService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}