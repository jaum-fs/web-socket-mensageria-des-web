package com.desweb.synchchat.util;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioLogin {
    @NotBlank(message = "O 'nickname' deve ser informado")
    private String nickname;

    @NotBlank(message = "A 'senha' deve ser informada")
    private String senha;
}