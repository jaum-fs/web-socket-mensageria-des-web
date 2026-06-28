package com.desweb.synchchat.controller;

import com.desweb.synchchat.dto.MensagemRequest;
import com.desweb.synchchat.service.MensagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MensagemService mensagemService;

    @MessageMapping("/mensagem")
    public void enviar(
            MensagemRequest request,
            Principal principal) {

        mensagemService.enviar(
                principal.getName(),
                request
        );
    }
}
