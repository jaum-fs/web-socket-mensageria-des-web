package com.desweb.synchchat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "tb_users")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Max(value = 30)
    @NotEmpty(message = "Nickname não pode ser nulo ou vazio.")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @NotEmpty(message = "A 'Senha' deve ser informada.")
    private String senha;

    //Construtor personalizado para usar no create
    public Usuario(String nickname, String senha, Role role) {
        this.nickname = nickname;
        this.senha = senha;
        this.role = role;
    }
}