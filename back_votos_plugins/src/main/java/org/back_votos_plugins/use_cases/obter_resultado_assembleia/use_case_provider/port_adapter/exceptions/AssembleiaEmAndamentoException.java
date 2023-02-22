package org.back_votos_plugins.use_cases.obter_resultado_assembleia.use_case_provider.port_adapter.exceptions;

import java.time.LocalDateTime;

public class AssembleiaEmAndamentoException extends RuntimeException {
    public AssembleiaEmAndamentoException(LocalDateTime fimAssembleia, LocalDateTime momentoRequest) {
        super("Esta assembleia ainda está em andamento. Tente novamente " +
                "após " + fimAssembleia + " para saber o resultado. \nMomento da solicitação: " + momentoRequest);
    }
}
