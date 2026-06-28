package com.desweb.synchchat.service;

import com.desweb.synchchat.dto.MensagemRequest;
import com.desweb.synchchat.dto.MensagemResponse;
import com.desweb.synchchat.model.Mensagem;
import com.desweb.synchchat.model.Room;
import com.desweb.synchchat.model.Usuario;
import com.desweb.synchchat.repository.MensagemRepository;
import com.desweb.synchchat.repository.RoomRepository;
import com.desweb.synchchat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MensagemService {

    private final SimpMessagingTemplate messagingTemplate;
    private final RoomRepository salaRepository;
    private final UserRepository usuarioRepository;
    private final MensagemRepository mensagemRepository;

    public List<MensagemResponse> listarPorSala(UUID salaId) {
        return mensagemRepository.findBySala_IdOrderByDataEnvio(salaId)
                .stream()
                .map(MensagemResponse::from)
                .toList();
    }

    public void enviar(String username,
                       MensagemRequest request) {

        // username é o ID do usuário em formato String (vem do Principal configurado pelo JwtAuthFilter)
        Usuario usuario =
                usuarioRepository.findById(Long.parseLong(username))
                        .orElseThrow();

        Room sala =
                salaRepository.findById(request.salaId())
                        .orElseThrow();

        Mensagem mensagem = new Mensagem();
        mensagem.setConteudo(request.conteudo());
        mensagem.setRemetente(usuario);
        mensagem.setSala(sala);
        mensagem.setDataEnvio(LocalDateTime.now());

        mensagemRepository.save(mensagem);

        MensagemResponse response =
                MensagemResponse.from(mensagem);

        messagingTemplate.convertAndSend(
                "/topic/salas/" + sala.getId(),
                response
        );
    }
}
