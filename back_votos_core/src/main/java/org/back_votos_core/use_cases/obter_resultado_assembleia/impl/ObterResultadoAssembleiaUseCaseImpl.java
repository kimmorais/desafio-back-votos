package org.back_votos_core.use_cases.obter_resultado_assembleia.impl;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.use_cases.obter_resultado_assembleia.ObterResultadoAssembleiaUseCase;
import org.back_votos_core.use_cases.obter_resultado_assembleia.impl.ports.ObterResultadoAssembleiaPort;
import org.back_votos_core.use_cases.obter_resultado_assembleia.input.ObterResultadoAssembleiaUseCaseInput;

@RequiredArgsConstructor
public class ObterResultadoAssembleiaUseCaseImpl implements ObterResultadoAssembleiaUseCase {

    private final ObterResultadoAssembleiaPort obterResultadoAssembleiaPort;

    @Override
    public Assembleia execute(ObterResultadoAssembleiaUseCaseInput input) {
        return this.obterResultadoAssembleiaPort.obterResultado(input);
    }
}
