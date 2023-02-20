package org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter.exceptions;

import java.time.LocalDateTime;

public class AssembleiaFinalizadaException extends RuntimeException {
    public AssembleiaFinalizadaException(LocalDateTime fimAssembleia, LocalDateTime horarioVoto) {
        super("Não foi possível votar nesta assembleia pois ela já foi encerrada." +
                "\nHorário final da assembleia: " + fimAssembleia + "." +
                "\nHorário do voto: " + horarioVoto + ".");
    }
}
