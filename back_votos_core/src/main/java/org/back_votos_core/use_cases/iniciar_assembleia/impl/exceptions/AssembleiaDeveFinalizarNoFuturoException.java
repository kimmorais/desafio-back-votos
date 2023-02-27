package org.back_votos_core.use_cases.iniciar_assembleia.impl.exceptions;

import java.time.LocalDateTime;

public class AssembleiaDeveFinalizarNoFuturoException extends RuntimeException {
    public AssembleiaDeveFinalizarNoFuturoException(LocalDateTime momentoRequest, LocalDateTime tempoAssembleia) {
        super("A assembleia deve ser finalizada em um momento futuro." +
                "\nMomento atual: " + momentoRequest +
                "\nMomento informado para finalizar a assembleia: " + tempoAssembleia);
    }
}
