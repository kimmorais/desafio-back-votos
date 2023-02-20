package org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter.exceptions;

import java.util.UUID;

public class AssembleiaNaoEncontradaException extends RuntimeException {
    public AssembleiaNaoEncontradaException(UUID idAssembleia) {
        super("Não foi possível encontrar uma assembleia com ID " + idAssembleia);
    }
}
