package com.desweb.synchchat.controller;

import com.desweb.synchchat.dto.MensagemResponse;
import com.desweb.synchchat.service.MensagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mensagens")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MensagemController {

    private final MensagemService mensagemService;

    @GetMapping("/sala/{salaId}")
    public ResponseEntity<List<MensagemResponse>> listarPorSala(@PathVariable UUID salaId) {
        return ResponseEntity.ok(mensagemService.listarPorSala(salaId));
    }
}