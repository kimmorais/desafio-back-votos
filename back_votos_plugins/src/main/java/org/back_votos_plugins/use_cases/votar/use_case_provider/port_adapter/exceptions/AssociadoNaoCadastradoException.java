package org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter.exceptions;

import java.util.UUID;

public class AssociadoNaoCadastradoException extends RuntimeException {
    public AssociadoNaoCadastradoException(UUID idAssociado) {
        super("Não existe um Associado cadastrado com ID " + idAssociado);
    }
}
