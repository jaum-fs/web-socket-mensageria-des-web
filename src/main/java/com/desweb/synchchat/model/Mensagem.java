package com.desweb.synchchat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Mensagem {

    @Id
    @GeneratedValue
    private Long id;

    private String conteudo;

    private LocalDateTime dataEnvio;

    @ManyToOne
    private Usuario remetente;

    @ManyToOne
    private Room sala;

}
