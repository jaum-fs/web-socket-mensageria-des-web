package com.desweb.synchchat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MensagemRequest

    (@NotNull(message = "O ID da sala é obrigatório")
     UUID salaId,

    @NotBlank(message = "O conteúdo da mensagem é obrigatório")
    String conteudo)

{

}
