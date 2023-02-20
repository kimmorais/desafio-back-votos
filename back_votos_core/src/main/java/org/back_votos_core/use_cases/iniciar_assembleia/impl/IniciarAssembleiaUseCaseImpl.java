package org.back_votos_core.use_cases.iniciar_assembleia.impl;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.use_cases.iniciar_assembleia.IniciarAssembleiaUseCase;
import org.back_votos_core.use_cases.iniciar_assembleia.impl.ports.IniciarAssembleiaPort;
import org.back_votos_core.use_cases.iniciar_assembleia.input.IniciarAssembleiaUseCaseInput;

@RequiredArgsConstructor
public class IniciarAssembleiaUseCaseImpl implements IniciarAssembleiaUseCase {

    private final IniciarAssembleiaPort iniciarAssembleiaPort;

    @Override
    public Assembleia execute(IniciarAssembleiaUseCaseInput input) {
        return this.iniciarAssembleiaPort.iniciarAssembleia(input);
    }
}
