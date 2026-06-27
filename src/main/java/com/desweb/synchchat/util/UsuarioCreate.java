package com.desweb.synchchat.util;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UsuarioCreate {
    @NotEmpty(message = "O 'nickname' deve ser informado.")
    private String nickname;

    @NotEmpty(message = "A 'senha' deve ser informada.")
    private String senha;
}