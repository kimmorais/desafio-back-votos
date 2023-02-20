package org.back_votos_plugins.use_cases.iniciar_assembleia.rest_endpoint.exceptions;

import java.time.LocalDateTime;

public class AssembleiaDeveFinalizarNoFuturoException extends RuntimeException {
    public AssembleiaDeveFinalizarNoFuturoException(LocalDateTime tempoAtual, LocalDateTime tempoAssembleiaFormatado) {
        super("A assembleia deve ser finalizada em um momento futuro." +
                "\nMomento atual: " + tempoAtual +
                "\nMomento informado para finalizar a assembleia: " + tempoAssembleiaFormatado);
    }
}
