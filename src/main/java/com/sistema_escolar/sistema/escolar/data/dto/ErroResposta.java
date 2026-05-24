package com.sistema_escolar.sistema.escolar.data.dto;

import java.util.List;

public record ErroResposta(String erro, int status, List<ErroCampo> campos) {
}
