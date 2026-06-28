package com.desweb.synchchat.dto;

import com.desweb.synchchat.model.Mensagem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MensagemResponse {

    private Long id;
    private UUID salaId;
    private Long remetenteId;
    private String remetenteNome;
    private String conteudo;
    private LocalDateTime dataEnvio;

    public static MensagemResponse from(Mensagem mensagem) {
        return MensagemResponse.builder()
                .id(mensagem.getId())
                .salaId(mensagem.getSala().getId())
                .remetenteId(mensagem.getRemetente().getId())
                .remetenteNome(mensagem.getRemetente().getNickname())
                .conteudo(mensagem.getConteudo())
                .dataEnvio(mensagem.getDataEnvio())
                .build();
    }
}