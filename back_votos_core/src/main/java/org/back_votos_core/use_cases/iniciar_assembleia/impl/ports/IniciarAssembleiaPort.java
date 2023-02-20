package org.back_votos_core.use_cases.iniciar_assembleia.impl.ports;

import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.use_cases.iniciar_assembleia.input.IniciarAssembleiaUseCaseInput;

public interface IniciarAssembleiaPort {

    Assembleia iniciarAssembleia(IniciarAssembleiaUseCaseInput input);
}
