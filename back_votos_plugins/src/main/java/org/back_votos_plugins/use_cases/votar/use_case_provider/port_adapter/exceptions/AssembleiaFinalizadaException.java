package org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter.exceptions;

import java.time.OffsetDateTime;

public class AssembleiaFinalizadaException extends RuntimeException {
    public AssembleiaFinalizadaException(OffsetDateTime fimAssembleia, OffsetDateTime horarioVoto) {
        super("Não foi possível votar nesta assembleia pois ela já foi encerrada." +
                "\nHorario final da assembleia: " + fimAssembleia + "." +
                "\nHorario do voto: " + horarioVoto + ".");
    }
}
