package org.back_votos_core.use_cases.iniciar_assembleia.factories;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.use_cases.iniciar_assembleia.IniciarAssembleiaUseCase;
import org.back_votos_core.use_cases.iniciar_assembleia.impl.IniciarAssembleiaUseCaseImpl;
import org.back_votos_core.use_cases.iniciar_assembleia.impl.ports.IniciarAssembleiaPort;

@RequiredArgsConstructor
public class IniciarAssembleiaUseCaseFactory {

    private final IniciarAssembleiaPort iniciarAssembleiaPort;

    public IniciarAssembleiaUseCase makeInstance() {
        return new IniciarAssembleiaUseCaseImpl(this.iniciarAssembleiaPort);
    }
}
