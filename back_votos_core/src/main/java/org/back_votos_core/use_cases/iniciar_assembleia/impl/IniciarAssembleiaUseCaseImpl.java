package org.back_votos_core.use_cases.iniciar_assembleia.impl;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.use_cases.iniciar_assembleia.IniciarAssembleiaUseCase;
import org.back_votos_core.use_cases.iniciar_assembleia.impl.exceptions.AssembleiaDeveFinalizarNoFuturoException;
import org.back_votos_core.use_cases.iniciar_assembleia.impl.ports.IniciarAssembleiaPort;
import org.back_votos_core.use_cases.iniciar_assembleia.input.IniciarAssembleiaUseCaseInput;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class IniciarAssembleiaUseCaseImpl implements IniciarAssembleiaUseCase {

    private final IniciarAssembleiaPort iniciarAssembleiaPort;

    @Override
    public Assembleia execute(IniciarAssembleiaUseCaseInput input) {
        validarTempoAssembleiaPassado(input.momentoRequest(), input.fimAssembleia());
        return this.iniciarAssembleiaPort.iniciarAssembleia(input);
    }

    private static void validarTempoAssembleiaPassado(LocalDateTime momentoRequest, LocalDateTime tempoAssembleia) {

        if (tempoAssembleia.isBefore(momentoRequest) || tempoAssembleia.isEqual(momentoRequest)) {
            throw new AssembleiaDeveFinalizarNoFuturoException(momentoRequest, tempoAssembleia);
        }
    }
}
